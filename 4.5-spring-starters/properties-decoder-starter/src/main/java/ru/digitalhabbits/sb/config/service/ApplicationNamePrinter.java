package ru.digitalhabbits.sb.config.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import static org.slf4j.LoggerFactory.getLogger;

public class ApplicationNamePrinter
        implements ApplicationInfoPrinter {
    private static final Logger logger = getLogger(ApplicationNamePrinter.class);

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void print() {
        logger.info("Hello from '{}'", applicationName);
    }
}
