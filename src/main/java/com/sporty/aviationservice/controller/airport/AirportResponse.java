package com.sporty.aviationservice.controller.airport;

import com.sporty.aviationservice.client.response.AirportsClientResponse;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
@Builder
public class AirportResponse {

  private String siteNumber;

  private String type;

  private String facilityName;

  private String faaIdent;

  private String icaoIdent;

  private String region;

  private String districtOffice;

  private String state;

  private String stateFull;

  private String county;

  private String city;

  private String ownership;

  private String use;

  private String manager;

  private String managerPhone;

  private String latitude;

  private String latitudeSec;

  private String longitude;

  private String longitudeSec;

  private String elevation;

  private String magneticVariation;

  private String tpa;

  private String vfrSectional;

  private String boundaryArtcc;

  private String boundaryArtccName;

  private String responsibleArtcc;

  private String responsibleArtccName;

  private String fssPhoneNumber;

  private String fssPhoneNumberTollfree;

  private String notamFacilityIdent;

  private String status;

  private String certificationTypeDate;

  private String customsAirportOfEntry;

  private String militaryJointUse;

  private String militaryLanding;

  private String lightingSchedule;

  private String beaconSchedule;

  private String controlTower;

  private String unicom;

  private String ctaf;

  private String effectiveDate;

  public static AirportResponse mapper(AirportsClientResponse.AirportDetails airportDetails) {
    var response = AirportResponse.builder().build();
    BeanUtils.copyProperties(airportDetails, response);
    return response;
  }
}
