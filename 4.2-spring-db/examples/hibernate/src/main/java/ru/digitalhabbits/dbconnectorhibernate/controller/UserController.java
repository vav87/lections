package ru.digitalhabbits.dbconnectorhibernate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.digitalhabbits.dbconnectorhibernate.dao.UserDaoWithSession;
import ru.digitalhabbits.dbconnectorhibernate.dto.BaseResponse;
import ru.digitalhabbits.dbconnectorhibernate.dto.UserDto;
import ru.digitalhabbits.dbconnectorhibernate.dto.UserMessagesDto;
import ru.digitalhabbits.dbconnectorhibernate.model.Message;
import ru.digitalhabbits.dbconnectorhibernate.model.User;
import ru.digitalhabbits.dbconnectorhibernate.utils.Context;

import static ru.digitalhabbits.dbconnectorhibernate.utils.Status.ERROR;
import static ru.digitalhabbits.dbconnectorhibernate.utils.Status.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    
    @Qualifier("UserDaoWithHibernate")
    private final UserDaoWithSession userDaoWithSession;

    @PostMapping
    public ResponseEntity<BaseResponse<UserDto>> create(@RequestBody UserDto dto) {
        Context<User> ctx = userDaoWithSession.create(new Context<>(User.from(dto)));

        if (ctx.status() == ERROR) {
            return ResponseEntity.ok(new BaseResponse<>(ctx.problem(), null));
        }

        return ResponseEntity.ok(new BaseResponse<>("ОК", UserDto.from(ctx.entity())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserDto>> read(@PathVariable Long id) {
        Context<User> context = userDaoWithSession.read(new Context<>(User.with(id)));

        if (context.status() == NOT_FOUND) {
            return ResponseEntity.notFound().build();
        }

        if (context.status() == ERROR) {
            return ResponseEntity.ok(new BaseResponse<>(context.problem(), null));
        }

        return ResponseEntity.ok(new BaseResponse<>("ОК", UserDto.from(context.entity())));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Message> update(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<BaseResponse<UserMessagesDto>> readAllMessages(@PathVariable Long id) {
        Context<User> context = userDaoWithSession.readAllMessages(new Context<>(User.with(id)));

        if (context.status() == NOT_FOUND) {
            return ResponseEntity.notFound().build();
        }

        if (context.status() == ERROR) {
            return ResponseEntity.ok(new BaseResponse<>(context.problem(), null));
        }

        return ResponseEntity.ok(new BaseResponse<>("ОК", UserMessagesDto.from(context.entity())));
    }
}
