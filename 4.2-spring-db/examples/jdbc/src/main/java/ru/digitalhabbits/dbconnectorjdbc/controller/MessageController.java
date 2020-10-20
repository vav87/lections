package ru.digitalhabbits.dbconnectorjdbc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.digitalhabbits.dbconnectorjdbc.dao.Crud;
import ru.digitalhabbits.dbconnectorjdbc.dto.BaseResponse;
import ru.digitalhabbits.dbconnectorjdbc.dto.MessageDto;
import ru.digitalhabbits.dbconnectorjdbc.model.Message;
import ru.digitalhabbits.dbconnectorjdbc.utils.Context;

import static ru.digitalhabbits.dbconnectorjdbc.utils.Status.ERROR;
import static ru.digitalhabbits.dbconnectorjdbc.utils.Status.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final Crud<Context<Message>> messageDao;

    @PostMapping
    public ResponseEntity<BaseResponse<MessageDto>> create(@RequestBody MessageDto dto) {
        Context<Message> context = messageDao.create(new Context<>(Message.from(dto)));

        if (context.status() == ERROR) {
            return ResponseEntity.ok(new BaseResponse<>(context.problem()));
        }

        return ResponseEntity.ok(new BaseResponse<>(MessageDto.from(context.entity())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MessageDto>> read(@PathVariable Long id) {
        Context<Message> context = messageDao.read(new Context<>(Message.withOnly(id)));

        if (context.status() == NOT_FOUND) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(context.problem()));
        }

        if (context.status() == ERROR) {
            return ResponseEntity.ok(new BaseResponse<>(context.problem()));
        }

        return ResponseEntity.ok(new BaseResponse<>(MessageDto.from(context.entity())));
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
