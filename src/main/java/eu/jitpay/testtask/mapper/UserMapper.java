package eu.jitpay.testtask.mapper;

import eu.jitpay.testtask.domain.Location;
import eu.jitpay.testtask.domain.User;
import eu.jitpay.testtask.dto.user.UpsertUserRequest;
import eu.jitpay.testtask.dto.user.UpsertUserResponse;
import eu.jitpay.testtask.dto.user.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "locations", ignore = true)
    })
    User mapFromUpsertUserRequest(UpsertUserRequest request);

    UpsertUserResponse mapToUpsertUserResponse(User user);

    @Mappings({
            @Mapping(target = "userId", source = "user.userId"),
            @Mapping(target = "location", source = "location")
    })
    UserInfo mapToUserWithLocationDto(User user, Location location);
}
