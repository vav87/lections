package ru.digitalhabbits.dbconnectorhibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.digitalhabbits.dbconnectorhibernate.model.Message;
import ru.digitalhabbits.dbconnectorhibernate.model.User;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
public final class UserMessagesDto {

    @Getter
    private final long id;
    @Getter
    private final UserDto user;
    @Getter
    private final List<MessageResponseDto> messages;
    
    public static UserMessagesDto from(User user) {
        List<MessageResponseDto> messageDtoList = new ArrayList<>();

        for (Message message : user.getMessages()) {
            messageDtoList.add(MessageResponseDto.from(message));
        }

        return UserMessagesDto.builder()
                .user(UserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .build())
                .messages(messageDtoList)
                .build();
    }
}
