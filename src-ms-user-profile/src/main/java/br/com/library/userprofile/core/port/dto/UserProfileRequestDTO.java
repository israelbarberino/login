package br.com.library.userprofile.core.port.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileRequestDTO {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String phone;
    private String cpf;

}
