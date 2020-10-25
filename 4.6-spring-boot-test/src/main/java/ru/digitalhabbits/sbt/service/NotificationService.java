package ru.digitalhabbits.sbt.service;

import ru.digitalhabbits.sbt.model.EventType;

public interface NotificationService {
    void notify(EventType eventType, String message);
}
