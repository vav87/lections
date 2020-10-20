package ru.digitalhabbits.dbconnectorhibernate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.digitalhabbits.dbconnectorhibernate.dao.MessageDaoWithSession;
import ru.digitalhabbits.dbconnectorhibernate.dto.*;
import ru.digitalhabbits.dbconnectorhibernate.model.Message;
import ru.digitalhabbits.dbconnectorhibernate.model.User;
import ru.digitalhabbits.dbconnectorhibernate.utils.Context;

import static ru.digitalhabbits.dbconnectorhibernate.utils.Status.ERROR;
import static ru.digitalhabbits.dbconnectorhibernate.utils.Status.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Qualifier("MessageDao")
    private final MessageDaoWithSession messageDaoWithSession;

    @PostMapping
    public ResponseEntity<BaseResponse<UserMessagesDto>> create(@RequestBody MessageRequestDto dto) {
        User user = messageDaoWithSession.createMessageWithUserId(dto.getUserId(), dto.getText());
        return ResponseEntity.ok(new BaseResponse<>("ОК", UserMessagesDto.from(user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MessageResponseDto>> read(@PathVariable Long id) {
        Context<Message> context = messageDaoWithSession.read(new Context<>(Message.with(id)));

        if (context.status() == NOT_FOUND) {
            return ResponseEntity.notFound().build();
        }

        if (context.status() == ERROR) {
            return ResponseEntity.ok(new BaseResponse<>(context.problem(), null));
        }

        return ResponseEntity.ok(new BaseResponse<>("ОК", MessageResponseDto.from(context.entity())));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Message> update(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable Long id) {
        return null;
    }
}
