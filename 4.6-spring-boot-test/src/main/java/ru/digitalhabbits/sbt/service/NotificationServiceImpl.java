package ru.digitalhabbits.sbt.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.digitalhabbits.sbt.model.EventType;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {
    private final Logger logger = getLogger(NotificationServiceImpl.class);

    private final MailService mailService;

    @Override
    public void notify(EventType eventType, String message) {
        final String formattedMessage = String.format("%s %s", eventType.name(), message);
        logger.info(formattedMessage);
        mailService.sendMessage(formattedMessage);
    }
}
