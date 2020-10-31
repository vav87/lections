package ru.digitalhabbits.sb.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.sb.config.service.ApplicationInfoPrinter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class StartDatePrinter
        implements ApplicationInfoPrinter {
    private static final Logger logger = getLogger(StartDatePrinter.class);

    @Override
    public void print() {
        logger.info("Application start date {}", DateTimeFormatter.ISO_DATE.format(LocalDateTime.now()));
    }
}
