package ru.digitalhabbits.dbconnectorjdbc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import ru.digitalhabbits.dbconnectorjdbc.dto.MessageDto;

@Accessors(fluent = true)
@AllArgsConstructor
@Builder
public final class Message {
    
    @Getter
    private final long id;
    @Getter
    private final String text;

    public static Message withOnly(long id) {
        return Message.builder()
                .id(id)
                .text(null)
                .build();
    }
    
    public static Message from(MessageDto dto) {
        return Message.builder()
                .id(dto.getId())
                .text(dto.getText())
                .build();
    }
}
