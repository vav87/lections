package ru.digitalhabbits.sbt.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MailServiceImpl
        implements MailService {
    private static final Logger logger = getLogger(MailServiceImpl.class);

    @Override
    public void sendMessage(String formattedMessage) {
        logger.info("Send message '{}'", formattedMessage);
    }
}
