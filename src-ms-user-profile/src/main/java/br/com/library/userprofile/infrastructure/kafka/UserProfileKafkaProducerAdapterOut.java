package br.com.library.userprofile.infrastructure.kafka;

import br.com.library.userprofile.core.port.out.UserProfileKafkaProducerPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserProfileKafkaProducerAdapterOut implements CommandLineRunner, UserProfileKafkaProducerPortOut {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${spring.topic.name.userprofile}")
    public String topic;

    @Override
    public void send(String message) {
        try {
            kafkaTemplate.send(topic, message);
            log.info("Message sent: " + message);
        } catch (Exception e) {
            log.error("Error sending message", e);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        send("message");
    }
}