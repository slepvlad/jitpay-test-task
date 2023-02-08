package eu.jitpay.testtask.mapper;

import eu.jitpay.testtask.domain.User;
import eu.jitpay.testtask.dto.user.UpsertUserRequest;
import eu.jitpay.testtask.dto.user.UpsertUserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapFromUpsertUserRequest(UpsertUserRequest request);
    UpsertUserResponse mapTopUpsertUserResponse(User user);
}
