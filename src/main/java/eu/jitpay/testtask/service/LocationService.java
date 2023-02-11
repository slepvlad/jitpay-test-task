package eu.jitpay.testtask.service;

import eu.jitpay.testtask.domain.Location;
import eu.jitpay.testtask.dto.user.UserLocationDataRequest;
import eu.jitpay.testtask.dto.user.UserLocationHistoryDto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface LocationService {

    void createUserLocation(UserLocationDataRequest request);

    Optional<UserLocationHistoryDto> getLocationHistory(UUID userId, LocalDateTime fromDateTime, LocalDateTime toDateTime);

    Optional<Location> getLatestLocation(UUID userId);
}
