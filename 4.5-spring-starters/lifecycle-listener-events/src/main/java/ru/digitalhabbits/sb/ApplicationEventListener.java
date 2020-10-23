package ru.digitalhabbits.sb;

import org.slf4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ApplicationEventListener {
    private static final Logger logger = getLogger(ApplicationEventListener.class);

    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationEvent(ApplicationStartedEvent event) {
        // ApplicationContext построен, но еще не были вызваны CommandLineRunner'ы
        logger.info("Application started");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Вся конфигурация выполнена, приложение готов обрабатывать запросы
        logger.info("Application ready");
    }
}
