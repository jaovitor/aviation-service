window.onload = function () {
  const STORAGE_KEYS = {
    access: "jwt_access",
    refresh: "jwt_refresh"
  };

  async function doRefresh() {
    const rt = localStorage.getItem(STORAGE_KEYS.refresh);
    if (!rt) throw new Error("No refresh token available");
    const res = await fetch("/auth/refresh", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ refresh_token: rt })
    });
    if (!res.ok) throw new Error("Refresh failed: " + (await res.text()));
    const data = await res.json(); // { access_token, refresh_token, token_type, expires_in }
    localStorage.setItem(STORAGE_KEYS.access, data.access_token);
    if (data.refresh_token) localStorage.setItem(STORAGE_KEYS.refresh, data.refresh_token);
    return data.access_token;
  }

  const ui = SwaggerUIBundle({
    url: "/v3/api-docs",
    dom_id: "#swagger-ui",
    deepLinking: true,
    presets: [SwaggerUIBundle.presets.apis, SwaggerUIStandalonePreset],
    layout: "StandaloneLayout",
    persistAuthorization: true,

    // Attach access token to every API call
    requestInterceptor: (req) => {
      const t = localStorage.getItem(STORAGE_KEYS.access);
      if (t && !req.loadSpec) {
        req.headers = req.headers || {};
        if (!req.headers["Authorization"]) {
          req.headers["Authorization"] = "Bearer " + t;
        }
      }
      return req;
    },

    // Auto-refresh on 401 once per request
    responseInterceptor: async (res) => {
      try {
        const orig = res.config || {};
        if (res.status === 401 && !orig._retriedWithRefresh) {
          const newAccess = await doRefresh();
          // retry original
          const newHeaders = Object.assign({}, orig.headers || {}, { Authorization: "Bearer " + newAccess });
          const retry = await fetch(orig.url, {
            method: orig.method || "GET",
            headers: newHeaders,
            body: orig.body
          });
          // Forge a Swagger-style response object
          retry.config = Object.assign({}, orig, { _retriedWithRefresh: true });
          return retry;
        }
      } catch (e) {
        console.warn("Auto-refresh failed:", e);
      }
      return res;
    },

    onComplete: () => {
      // Add login box to topbar (same as before, now storing refresh too)
      const topbar = document.querySelector(".topbar .download-url-wrapper") || document.querySelector(".topbar");
      if (!topbar) return;

      const box = document.createElement("div");
      box.className = "login-box";
      box.innerHTML = `
        <input id="sw-user" type="text" placeholder="username" />
        <input id="sw-pass" type="password" placeholder="password" />
        <button id="sw-login">Login</button>
        <button id="sw-logout" title="Clear stored token">Logout</button>
      `;
      topbar.appendChild(box);

      const userI = document.getElementById("sw-user");
      const passI = document.getElementById("sw-pass");
      const loginB = document.getElementById("sw-login");
      const logoutB = document.getElementById("sw-logout");

      function preauthorize(accessToken) {
        try {
          if (ui && ui.preauthorizeApiKey) {
            ui.preauthorizeApiKey("bearerAuth", "Bearer " + accessToken);
          }
        } catch (e) {
          console.warn("Preauthorize failed:", e);
        }
      }

      const existingAccess = localStorage.getItem(STORAGE_KEYS.access);
      if (existingAccess) preauthorize(existingAccess);

      loginB.onclick = async () => {
        const username = userI.value.trim();
        const password = passI.value;
        if (!username || !password) {
          alert("Enter username and password");
          return;
        }
        try {
          const res = await fetch("/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
          });
          if (!res.ok) throw new Error(await res.text());
          const data = await res.json(); // {access_token, refresh_token, ...}
          localStorage.setItem(STORAGE_KEYS.access, data.access_token);
          if (data.refresh_token) localStorage.setItem(STORAGE_KEYS.refresh, data.refresh_token);
          preauthorize(data.access_token);
          alert("Logged in. Tokens stored & applied.");
        } catch (err) {
          console.error(err);
          alert("Login error: " + err.message);
        }
      };

      logoutB.onclick = () => {
        localStorage.removeItem(STORAGE_KEYS.access);
        localStorage.removeItem(STORAGE_KEYS.refresh);
        try { ui.authActions.logout(); } catch (e) {}
        alert("Logged out. Tokens cleared.");
      };
    }
  });

  window.ui = ui;
};
