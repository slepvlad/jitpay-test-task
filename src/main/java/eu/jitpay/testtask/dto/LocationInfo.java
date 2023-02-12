package eu.jitpay.testtask.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LocationInfo {

    private LocalDateTime createdOn;
    private LocationDto location;
}
