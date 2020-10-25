package ru.digitalhabbits.sbt.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.digitalhabbits.sbt.repository.UserRepository;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class UserServiceConfiguration {

    @Bean
    public NotificationService notificationService() {
        return mock(NotificationService.class);
    };

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository, notificationService());
    }
}
