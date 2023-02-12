package eu.jitpay.testtask.service;

import eu.jitpay.testtask.dto.UpsertUserRequest;
import eu.jitpay.testtask.dto.UpsertUserResponse;
import eu.jitpay.testtask.dto.UserInfo;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UpsertUserResponse upsertUser(UpsertUserRequest request);

    Optional<UserInfo> getUserInfoWithLatestLocation(UUID userId);
}
