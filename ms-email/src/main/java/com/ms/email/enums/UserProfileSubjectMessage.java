package com.ms.email.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserProfileSubjectMessage {

    USER_PROFILE_CREATED("[AVISO DE SEGURANÇA] - Um perfil foi criaado utilizando seu e-mail."),
    USER_PROFILE_UPDATED("[AVISO DE SEGURANÇA] - Seu perfil foi atualizado."),
    USER_PROFILE_DELETED("[AVISO DE SEGURANÇA] - Solicitação de exclusão de perfil."),;

    private final String message;

}