package eu.jitpay.testtask.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserLocationHistoryDto {

    private UUID userId;
    List<LocationInfo> locations;
}
