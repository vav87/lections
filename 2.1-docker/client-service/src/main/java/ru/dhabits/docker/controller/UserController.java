package ru.dhabits.docker.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dhabits.docker.model.UserEntity;
import ru.dhabits.docker.repository.UserRepository;

@Profile("prod")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable String id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/create")
    public UserEntity create(@RequestBody UserEntity user) {
        return userRepository.save(user);
    }
}
