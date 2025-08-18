package com.sporty.aviationservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.sporty.aviationservice.client.AviationApiClient;
import com.sporty.aviationservice.client.response.AirportsClientResponse;
import com.sporty.aviationservice.controller.airport.AirportResponse;
import com.sporty.aviationservice.domain.Error;
import com.sporty.aviationservice.exception.AviationApiException;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class AirportServiceTest {

  @Mock private AviationApiClient aviationApiClient;

  private AirportService airportService;

  @BeforeEach
  void setUp() {
    MeterRegistry registry = new SimpleMeterRegistry();
    Metrics.addRegistry(registry);
    airportService = new AirportService(aviationApiClient, registry);
  }

  @Test
  void getAirportReturnsMappedResponseWhenClientSucceeds() {
    String icao = "LAX";
    var clientResponse = createAirportsClientResponse();
    var expectedResponse = createAirportResponse();

    when(aviationApiClient.getAirportByApt(icao)).thenReturn(clientResponse);
    var result = airportService.getAirport(icao);

    assertEquals(expectedResponse, result);
  }

  @Test
  void getAirportThrowsExceptionWhenClientFails() {
    String icao = "INVALID";
    when(aviationApiClient.getAirportByApt(icao))
        .thenThrow(new AviationApiException(Error.AVIATION_API_ERROR, HttpStatus.NOT_FOUND));
    assertThrows(AviationApiException.class, () -> airportService.getAirport(icao));
  }

  private AirportsClientResponse createAirportsClientResponse() {
    var airportDetails =
        AirportsClientResponse.AirportDetails.builder()
            .siteNumber("01818.*A")
            .type("AIRPORT")
            .facilityName("LOS ANGELES INTL")
            .faaIdent("LAX")
            .icaoIdent("KLAX")
            .region("AWP")
            .districtOffice("LAX")
            .state("CA")
            .stateFull("CALIFORNIA")
            .county("LOS ANGELES")
            .city("LOS ANGELES")
            .ownership("PU")
            .use("PU")
            .manager("VIJI PRASAD")
            .managerPhone("424-646-8251")
            .latitude("33-56-32.9870N")
            .latitudeSec("122192.9870N")
            .longitude("118-24-28.9750W")
            .longitudeSec("426268.9750W")
            .elevation("127")
            .magneticVariation("12E")
            .tpa("")
            .vfrSectional("LOS ANGELES")
            .boundaryArtcc("ZLA")
            .boundaryArtccName("LOS ANGELES")
            .responsibleArtcc("ZLA")
            .responsibleArtccName("LOS ANGELES")
            .fssPhoneNumber("")
            .fssPhoneNumberTollfree("1-800-WX-BRIEF")
            .notamFacilityIdent("LAX")
            .status("O")
            .certificationTypeDate("I E S 05/1973")
            .customsAirportOfEntry("N")
            .militaryJointUse("")
            .militaryLanding("")
            .lightingSchedule("")
            .beaconSchedule("SS-SR")
            .controlTower("Y")
            .unicom("122.950")
            .ctaf("")
            .effectiveDate("11/04/2021")
            .build();

    var response = new AirportsClientResponse();
    response.addEntry("LAX", List.of(airportDetails));
    return response;
  }

  private AirportResponse createAirportResponse() {
    return AirportResponse.builder()
        .siteNumber("01818.*A")
        .type("AIRPORT")
        .facilityName("LOS ANGELES INTL")
        .faaIdent("LAX")
        .icaoIdent("KLAX")
        .region("AWP")
        .districtOffice("LAX")
        .state("CA")
        .stateFull("CALIFORNIA")
        .county("LOS ANGELES")
        .city("LOS ANGELES")
        .ownership("PU")
        .use("PU")
        .manager("VIJI PRASAD")
        .managerPhone("424-646-8251")
        .latitude("33-56-32.9870N")
        .latitudeSec("122192.9870N")
        .longitude("118-24-28.9750W")
        .longitudeSec("426268.9750W")
        .elevation("127")
        .magneticVariation("12E")
        .tpa("")
        .vfrSectional("LOS ANGELES")
        .boundaryArtcc("ZLA")
        .boundaryArtccName("LOS ANGELES")
        .responsibleArtcc("ZLA")
        .responsibleArtccName("LOS ANGELES")
        .fssPhoneNumber("")
        .fssPhoneNumberTollfree("1-800-WX-BRIEF")
        .notamFacilityIdent("LAX")
        .status("O")
        .certificationTypeDate("I E S 05/1973")
        .customsAirportOfEntry("N")
        .militaryJointUse("")
        .militaryLanding("")
        .lightingSchedule("")
        .beaconSchedule("SS-SR")
        .controlTower("Y")
        .unicom("122.950")
        .ctaf("")
        .effectiveDate("11/04/2021")
        .build();
  }
}
