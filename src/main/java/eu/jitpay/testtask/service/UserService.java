package eu.jitpay.testtask.service;

import eu.jitpay.testtask.dto.user.UpsertUserRequest;
import eu.jitpay.testtask.dto.user.UpsertUserResponse;

public interface UserService {

    UpsertUserResponse upsertUser(UpsertUserRequest request);
}
