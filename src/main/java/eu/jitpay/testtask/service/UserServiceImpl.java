package eu.jitpay.testtask.service;

import eu.jitpay.testtask.dto.user.UpsertUserRequest;
import eu.jitpay.testtask.dto.user.UpsertUserResponse;
import eu.jitpay.testtask.mapper.UserMapper;
import eu.jitpay.testtask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public UpsertUserResponse upsertUser(UpsertUserRequest request) {
        var user = userMapper.mapFromUpsertUserRequest(request);
        return userMapper.mapTopUpsertUserResponse(
                userRepository.save(user)
        );
    }
}
