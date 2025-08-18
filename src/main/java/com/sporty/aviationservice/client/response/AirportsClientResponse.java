package com.sporty.aviationservice.client.response;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sporty.aviationservice.domain.Error;
import com.sporty.aviationservice.exception.GeneralException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirportsClientResponse {

  private final Map<String, List<AirportDetails>> airportsMap = new HashMap<>();

  @JsonAnySetter
  public void addEntry(String aptCode, List<AirportDetails> airports) {
    airportsMap.put(aptCode, airports);
  }

  @JsonIgnore
  public AirportDetails getAirportDetails() {
    return airportsMap.values().stream()
        .filter(airports -> airports != null && !airports.isEmpty())
        .findFirst()
        .orElseThrow(() -> new GeneralException(Error.AIRPORT_NOT_FOUND, HttpStatus.NOT_FOUND))
        .get(0);
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class AirportDetails {
    @JsonProperty("site_number")
    private String siteNumber;

    @JsonProperty("type")
    private String type;

    @JsonProperty("facility_name")
    private String facilityName;

    @JsonProperty("faa_ident")
    private String faaIdent;

    @JsonProperty("icao_ident")
    private String icaoIdent;

    @JsonProperty("region")
    private String region;

    @JsonProperty("district_office")
    private String districtOffice;

    @JsonProperty("state")
    private String state;

    @JsonProperty("state_full")
    private String stateFull;

    @JsonProperty("county")
    private String county;

    @JsonProperty("city")
    private String city;

    @JsonProperty("ownership")
    private String ownership;

    @JsonProperty("use")
    private String use;

    @JsonProperty("manager")
    private String manager;

    @JsonProperty("manager_phone")
    private String managerPhone;

    @JsonProperty("latitude")
    private String latitude;

    @JsonProperty("latitude_sec")
    private String latitudeSec;

    @JsonProperty("longitude")
    private String longitude;

    @JsonProperty("longitude_sec")
    private String longitudeSec;

    @JsonProperty("elevation")
    private String elevation;

    @JsonProperty("magnetic_variation")
    private String magneticVariation;

    @JsonProperty("tpa")
    private String tpa;

    @JsonProperty("vfr_sectional")
    private String vfrSectional;

    @JsonProperty("boundary_artcc")
    private String boundaryArtcc;

    @JsonProperty("boundary_artcc_name")
    private String boundaryArtccName;

    @JsonProperty("responsible_artcc")
    private String responsibleArtcc;

    @JsonProperty("responsible_artcc_name")
    private String responsibleArtccName;

    @JsonProperty("fss_phone_number")
    private String fssPhoneNumber;

    @JsonProperty("fss_phone_numer_tollfree")
    private String fssPhoneNumberTollfree;

    @JsonProperty("notam_facility_ident")
    private String notamFacilityIdent;

    @JsonProperty("status")
    private String status;

    @JsonProperty("certification_typedate")
    private String certificationTypeDate;

    @JsonProperty("customs_airport_of_entry")
    private String customsAirportOfEntry;

    @JsonProperty("military_joint_use")
    private String militaryJointUse;

    @JsonProperty("military_landing")
    private String militaryLanding;

    @JsonProperty("lighting_schedule")
    private String lightingSchedule;

    @JsonProperty("beacon_schedule")
    private String beaconSchedule;

    @JsonProperty("control_tower")
    private String controlTower;

    @JsonProperty("unicom")
    private String unicom;

    @JsonProperty("ctaf")
    private String ctaf;

    @JsonProperty("effective_date")
    private String effectiveDate;
  }
}
