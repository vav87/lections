package ru.digitalhabbits.dbconnectorjdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.digitalhabbits.dbconnectorjdbc.model.Message;

@AllArgsConstructor
@Builder
public final class MessageDto {

    @Getter
    private final long id;
    @Getter
    private final String text;
    
    public static MessageDto from(Message model) {
        return MessageDto.builder()
                .id(model.id())
                .text(model.text())
                .build();
    }
}
