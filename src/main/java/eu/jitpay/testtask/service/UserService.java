package eu.jitpay.testtask.service;

import eu.jitpay.testtask.dto.user.UpsertUserRequest;
import eu.jitpay.testtask.dto.user.UpsertUserResponse;
import eu.jitpay.testtask.dto.user.UserWithLocationDto;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UpsertUserResponse upsertUser(UpsertUserRequest request);

    Optional<UserWithLocationDto> getUserDataWithLatestLocation(UUID userId);
}
