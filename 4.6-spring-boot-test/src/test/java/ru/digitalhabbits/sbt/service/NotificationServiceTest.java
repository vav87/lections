package ru.digitalhabbits.sbt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.digitalhabbits.sbt.model.EventType;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ActiveProfiles("service-test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { NotificationServiceConfiguration.class, MockRepositoriesConfiguration.class })
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MailService mailService;

    @Test
    void testNotify() {
        notificationService.notify(EventType.CREATED, "TEST");
        Mockito.verify(mailService, times(1)).sendMessage(anyString());
    }
}