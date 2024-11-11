package com.ms.email.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserProfileMailMessage {

    WELCOME("Seja bem-vindo! Esperamos que você aproveite a nossa plataforma!"),
    PASSWORD_RESET("Sua senha foi atualizada com sucesso!"),
    DATA_UPDATED("Seus dados foram atualizados com sucesso!"),
    ACCOUNT_DELETED("Recebemos o seu pedido de exclusão de conta. Sentimos muito! Esperamos que volte logo!"),;

    private final String message;

}
