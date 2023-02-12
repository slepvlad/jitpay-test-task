package eu.jitpay.testtask.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserLocationDataRequest {

    @NotNull
    private UUID userId;
    @NotNull
    private LocalDateTime createdOn;
    @NotNull
    private LocationDto location;

}
