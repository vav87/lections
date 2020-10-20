package ru.digitalhabbits.dbconnectorhibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.digitalhabbits.dbconnectorhibernate.model.Message;
import ru.digitalhabbits.dbconnectorhibernate.model.User;

@AllArgsConstructor
@Builder
public final class UserDto {

    @Getter
    private final long id;
    @Getter
    private final String name;

    public static UserDto from(User model) {
        return UserDto.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }
}
