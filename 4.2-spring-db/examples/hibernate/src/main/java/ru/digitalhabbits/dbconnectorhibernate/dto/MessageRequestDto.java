package ru.digitalhabbits.dbconnectorhibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.digitalhabbits.dbconnectorhibernate.model.Message;

@AllArgsConstructor
@Builder
public final class MessageRequestDto {

    @Getter
    private final long userId;

    @Getter
    private final String text;
    
}
