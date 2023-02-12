package eu.jitpay.testtask.service;

import eu.jitpay.testtask.domain.Location;
import eu.jitpay.testtask.dto.UserLocationDataRequest;
import eu.jitpay.testtask.dto.UserLocationHistoryDto;
import eu.jitpay.testtask.exceptions.BadRequestException;
import eu.jitpay.testtask.mapper.LocationMapper;
import eu.jitpay.testtask.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService{

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    @Value("${location.max-time-period-in-days}")
    private Integer maxTimePeriodInDays;
    @Override
    public void createUserLocation(UserLocationDataRequest request) {
        var location = locationMapper.convertFromUserLocationDataRequest(request);
        locationRepository.save(location);
    }

    @Override
    public Optional<UserLocationHistoryDto> getLocationHistory(
            UUID userId,
            LocalDateTime fromDateTime,
            LocalDateTime toDateTime
    ) {

        validateLocationHistoryTimeRange(fromDateTime, toDateTime);
        var locations = locationRepository.findByUserIdAndCreatedOnBetween(userId, fromDateTime, toDateTime);
        return locationMapper.convertToUserLocationHistoryDto(locations);
    }

    @Override
    public Optional<Location> getLatestLocation(UUID userId) {
        return locationRepository.findLastByUserId(userId);
    }


    private void validateLocationHistoryTimeRange(
            LocalDateTime fromDateTime,
            LocalDateTime toDateTime
    ) {
        if(fromDateTime.isAfter(toDateTime)) {
            log.warn("Date And Time from {} is after Date And Time to {}", fromDateTime, toDateTime);
            throw new BadRequestException("Date And Time from is after Date And Time to");
        }

        if(fromDateTime.plusDays(maxTimePeriodInDays).isBefore(toDateTime)) {
            log.warn("Max time period from {} and to {} is longer than {}",fromDateTime, toDateTime, maxTimePeriodInDays);
            throw new BadRequestException("Max time period is to long. Try to reduce time range");
        }
    }
}
