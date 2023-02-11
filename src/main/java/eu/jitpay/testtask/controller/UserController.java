package eu.jitpay.testtask.controller;

import eu.jitpay.testtask.dto.user.UpsertUserRequest;
import eu.jitpay.testtask.dto.user.UpsertUserResponse;
import eu.jitpay.testtask.dto.user.UserWithLocationDto;
import eu.jitpay.testtask.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UpsertUserResponse> upsertUser(
            @Valid @RequestBody UpsertUserRequest request
    ) {
        return ResponseEntity.ok(userService.upsertUser(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserWithLocationDto> getUserDataWithLatestLocation(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.of(userService.getUserDataWithLatestLocation(userId));
    }
}
