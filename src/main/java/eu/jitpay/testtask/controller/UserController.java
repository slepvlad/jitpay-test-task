package eu.jitpay.testtask.controller;

import eu.jitpay.testtask.dto.UpsertUserRequest;
import eu.jitpay.testtask.dto.UpsertUserResponse;
import eu.jitpay.testtask.dto.UserInfo;
import eu.jitpay.testtask.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(
            summary = "Create or update user",
            description = "Method creates or update existed user." +
                    " Email should be unique"
    )
    public ResponseEntity<UpsertUserResponse> upsertUser(
            @Valid @RequestBody UpsertUserRequest request
    ) {
        return ResponseEntity.ok(userService.upsertUser(request));
    }

    @GetMapping("/{userId}")
    @Operation(
            summary = "Get user info with latest location",
            description = "Get user info with latest location"
    )
    public ResponseEntity<UserInfo> getUserInfoWithLatestLocation(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.of(userService.getUserInfoWithLatestLocation(userId));
    }
}
