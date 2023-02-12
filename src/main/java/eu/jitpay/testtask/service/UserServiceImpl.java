package eu.jitpay.testtask.service;

import eu.jitpay.testtask.dto.user.UpsertUserRequest;
import eu.jitpay.testtask.dto.user.UpsertUserResponse;
import eu.jitpay.testtask.dto.user.UserInfo;
import eu.jitpay.testtask.mapper.UserMapper;
import eu.jitpay.testtask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LocationService locationService;
    private final UserMapper userMapper;


    @Override
    public UpsertUserResponse upsertUser(UpsertUserRequest request) {
        var user = userMapper.mapFromUpsertUserRequest(request);
        return userMapper.mapToUpsertUserResponse(
                userRepository.save(user)
        );
    }

    @Override
    @Transactional
    public Optional<UserInfo> getUserInfoWithLatestLocation(UUID userId) {
        var location = locationService.getLatestLocation(userId).orElse(null);
        return userRepository.findById(userId)
                .map(user -> userMapper.mapToUserWithLocationDto(user, location));
    }
}
