package ru.digitalhabbits.sbt.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.spy;

@TestConfiguration
public class NotificationServiceConfiguration {

    @Bean
    public MailService mailService() {
        return spy(MailService.class);
    }

    @Bean
    public NotificationService notificationService() {
        return new NotificationServiceImpl(mailService());
    }
}
