package br.com.library.userprofile.core.port.out;

public interface UserProfileKafkaProducerPortOut {

    void send(String message);
}
