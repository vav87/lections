package ru.digitalhabbits.dbconnectorhibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.digitalhabbits.dbconnectorhibernate.model.Message;

@AllArgsConstructor
@Builder
public final class MessageResponseDto {

    @Getter
    private final long id;
    @Getter
    private final String text;
    
    public static MessageResponseDto from(Message model) {
        return MessageResponseDto.builder()
                .id(model.getId())
                .text(model.getText())
                .build();
    }
}
