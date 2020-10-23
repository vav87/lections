package ru.digitalhabbits.sb;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationStartedEventListener
        implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        // Событие пробрасывается как только зарегистрированы listerner'ы,
        // перед построением environment и applicationContext.
        // Поэтому его нужно регистрировать через spring.factories, т.к. никакие bean'ы
        // еще не найдены
        System.out.println("Application starting...");
    }
}
