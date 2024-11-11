package br.com.library.userprofile.core.port.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageDto {

    private String message;
    private String name;
    private String email;
    private String date;
}
