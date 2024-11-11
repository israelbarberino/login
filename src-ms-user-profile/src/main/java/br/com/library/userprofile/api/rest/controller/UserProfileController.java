package br.com.library.userprofile.api.rest.controller;

import br.com.library.userprofile.core.domain.entity.UserProfileEntity;
import br.com.library.userprofile.core.port.dto.UserProfileRequestDTO;
import br.com.library.userprofile.core.port.in.UserProfileUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileUseCase userProfileUseCase;

    @PostMapping("/create")
    public ResponseEntity<UserProfileEntity> create(@RequestBody UserProfileRequestDTO userProfileRequestDTO) {
        log.info("Creating user profile: " + userProfileRequestDTO.toString());
        return ResponseEntity.ok(userProfileUseCase.createUserProfile(userProfileRequestDTO));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test");
    }
}
