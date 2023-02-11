package eu.jitpay.testtask.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserWithLocationDto {

    private UUID userId;
    private String email;
    private String firstName;
    private String secondName;
    private LocationDto location;
}
