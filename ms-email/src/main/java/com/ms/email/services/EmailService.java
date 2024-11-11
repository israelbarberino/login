package com.ms.email.services;

import com.ms.email.dtos.MessageDto;
import com.ms.email.enums.StatusEmail;
import com.ms.email.enums.UserProfileSubjectMessage;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;

    public void sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            var message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);
            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } {
            emailRepository.save(emailModel);
        }
    }

    public Optional<EmailModel> findById(UUID emailId) {
        return emailRepository.findById(emailId);
    }

    public EmailModel messageToEmailMapper(MessageDto messageDto) {
        var emailModel = new EmailModel();
        emailModel.setEmailFrom("teste@teste.com");
        emailModel.setEmailTo(messageDto.getEmail());
        extracted(messageDto, emailModel);
        sendEmail(emailModel);
        return emailModel;
    }

    private static void extracted(MessageDto messageDto, EmailModel emailModel) {
        try {
            var name = messageDto.getName();
            var status = UserProfileSubjectMessage.valueOf(messageDto.getMessage());
            var subject = "";
            var text = switch (status) {
                case USER_PROFILE_CREATED -> {
                    subject = "[AVISO DE SEGURANÇA] - Um perfil foi criado utilizando essa conta e-mail.";
                    yield "Bem-vindo, " + name +"! Seu cadastro foi concluído com SUCESSO." +
                            "\n\n" +
                            "Já contamos o pagamento dos R$ 2.000,00 debitados da sua conta." +
                            "\n" +
                            "Fique tranquilo, para sua comodidade, não será necessário entrar em contato com o banco, pois já cuidadmos de tudo para você."+
                            "\n"+
                            "Agradecemos a confiança e estamos à disposição para qualquer dúvida." +
                            "\n\n" +
                            "Atenciosamente, " +
                            "\n" +
                            "Equipe de Ganhe Mais - Aposta Fácil";
                }
                case USER_PROFILE_UPDATED -> {
                    subject = "User Profile Updated";
                    yield "Your profile has been updated with the latest changes.";
                }
                case USER_PROFILE_DELETED -> {
                    subject = "User Profile Deleted";
                    yield "Your profile has been deleted. If this was a mistake, please contact support.";
                }
            };

            emailModel.setSubject(subject);
            emailModel.setText(text);
        } catch (IllegalArgumentException e) {
            emailModel.setSubject("Unknown Status");
            emailModel.setText("The user profile status provided is not recognized.");
        }
    }

}
