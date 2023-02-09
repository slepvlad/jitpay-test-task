package eu.jitpay.testtask.controller;

import eu.jitpay.testtask.dto.user.UserLocationDataRequest;
import eu.jitpay.testtask.dto.user.UserLocationHistoryDto;
import eu.jitpay.testtask.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Void> createUserLocation(
            @Valid @RequestBody UserLocationDataRequest request
    ) {
        locationService.createUserLocation(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserLocationHistoryDto> getLocationHistory(
            @PathVariable UUID userId,
            @RequestParam("from") LocalDateTime fromDateTime,
            @RequestParam("to") LocalDateTime toDateTime
    ) {
        return ResponseEntity.of(locationService.getLocationHistory(userId, fromDateTime, toDateTime));
    }
}
