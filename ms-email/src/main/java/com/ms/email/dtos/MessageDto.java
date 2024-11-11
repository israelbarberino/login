package com.ms.email.dtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private String message;
    private String name;
    private String email;
    private String date;
}
