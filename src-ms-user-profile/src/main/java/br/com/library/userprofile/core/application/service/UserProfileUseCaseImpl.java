package br.com.library.userprofile.core.application.service;

import br.com.library.userprofile.core.domain.entity.UserProfileEntity;
import br.com.library.userprofile.core.port.dto.MessageDto;
import br.com.library.userprofile.core.port.dto.UserProfileRequestDTO;
import br.com.library.userprofile.core.port.in.UserProfileUseCase;
import br.com.library.userprofile.infrastructure.kafka.UserProfileKafkaProducerAdapterOut;
import br.com.library.userprofile.infrastructure.repositories.UserProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@AllArgsConstructor
public class UserProfileUseCaseImpl implements UserProfileUseCase {

    private final UserProfileKafkaProducerAdapterOut userProfileKafkaProducerAdapterOut;
    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfileEntity createUserProfile(UserProfileRequestDTO userProfileRequestDTO) {
        verification(userProfileRequestDTO);
        passwordVerification(userProfileRequestDTO);
        userProfileRepository.save(responseMapper(userProfileRequestDTO));
        publisher(userProfileRequestDTO);
        return responseMapper(userProfileRequestDTO);
    }

    private void verification(UserProfileRequestDTO userProfileRequestDTO) {
        if (userProfileRequestDTO.getName() == null) {
            throw new IllegalArgumentException("Name is required");
        }
        if (userProfileRequestDTO.getEmail() == null) {
            throw new IllegalArgumentException("Email is required");
        }
        if (userProfileRequestDTO.getPassword() == null) {
            throw new IllegalArgumentException("Password is required");
        }
        if (userProfileRequestDTO.getConfirmPassword() == null) {
            throw new IllegalArgumentException("Confirm password is required");
        }
        if (userProfileRequestDTO.getPhone() == null) {
            throw new IllegalArgumentException("Phone is required");
        }
        if (userProfileRequestDTO.getCpf() == null) {
            throw new IllegalArgumentException("CPF is required");
        }
    }

    private void passwordVerification(UserProfileRequestDTO userProfileRequestDTO) {
        if (!userProfileRequestDTO.getPassword().equals(userProfileRequestDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and confirm password must be the same");
        }
    }

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private void publisher(UserProfileRequestDTO userProfileRequestDTO) {
        var message = MessageDto.builder()
                .message("USER_PROFILE_CREATED")
                .name(userProfileRequestDTO.getName())
                .email(userProfileRequestDTO.getEmail())
                .date("null")
                .build();
        final var newMessage = messageParser(message);
        userProfileKafkaProducerAdapterOut.send(newMessage);
    }

    private static String messageParser(MessageDto message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error parsing message: " + e.getMessage(), e);
        }
    }

    private static UserProfileEntity responseMapper(UserProfileRequestDTO userProfileRequestDTO) {
       var userProfileEntity = new UserProfileEntity();
       userProfileEntity.setName(userProfileRequestDTO.getName());
       userProfileEntity.setEmail(userProfileRequestDTO.getEmail());
       userProfileEntity.setPassword(userProfileRequestDTO.getPassword());
       userProfileEntity.setPhone(userProfileRequestDTO.getPhone());
       userProfileEntity.setCpf(userProfileRequestDTO.getCpf());
       final var subscribedDateFormatter = getString();
       userProfileEntity.setSubscribeDate("10-11-2024");
       log.info("User profile created: " + userProfileEntity);
       return userProfileEntity;
    }

    private static String getString() {
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        var dateTimeInSaoPaulo = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        return dateTimeInSaoPaulo.format(formatter);
    }
}