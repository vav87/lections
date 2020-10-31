package ru.digitalhabbits.sbt.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.digitalhabbits.sbt.model.CreateUserRequest;
import ru.digitalhabbits.sbt.model.UserInfoResponse;
import ru.digitalhabbits.sbt.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserInfoResponse> users() {
        return userService.findAllUsers();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserInfoResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }
}
