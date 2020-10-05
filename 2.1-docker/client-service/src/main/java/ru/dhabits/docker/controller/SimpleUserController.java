package ru.dhabits.docker.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dhabits.docker.model.UserEntity;

@RestController
@RequestMapping("/mock/user")
public class SimpleUserController {

    Map<String, UserEntity> userRepository = new HashMap<>();

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable String id) {
        return userRepository.get(id);
    }

    @PostMapping("/create")
    public UserEntity create(@RequestBody UserEntity user) {
        user.setId(UUID.randomUUID().toString());
        userRepository.put(user.getId(), user);
        return user;
    }
}
