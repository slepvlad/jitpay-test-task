package eu.jitpay.testtask.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpsertUserRequest {

    @NotNull
    private UUID userId;
    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String secondName;
}
