package com.ms.email.services.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.email.dtos.MessageDto;
import com.ms.email.services.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class Listener {

    private final RabbitTemplate rabbitTemplate;
    private final EmailService emailService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "createduser.notification", groupId = "ms-email-group")
    public String listen(String message, Acknowledgment acknowledgment) {
        try {
            var messageDto = objectMapper.readValue(message, MessageDto.class);
            log.info("Mensagem recebida: " + message);
            emailService.messageToEmailMapper(messageDto);
            rabbitTemplate.convertAndSend("ms.email", message);
            acknowledgment.acknowledge();
            return message;
        } catch (IOException e) {
            log.error("Erro ao processar a mensagem", e);
            acknowledgment.acknowledge();
            return "Erro no processamento da mensagem";
        }
    }
}
