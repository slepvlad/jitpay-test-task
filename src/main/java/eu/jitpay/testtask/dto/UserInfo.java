package eu.jitpay.testtask.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserInfo {

    private UUID userId;
    private String email;
    private String firstName;
    private String secondName;
    private LocationDto location;
}
