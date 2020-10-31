package ru.digitalhabbits.sbt.service;

import ru.digitalhabbits.sbt.model.CreateUserRequest;
import ru.digitalhabbits.sbt.model.UserInfoResponse;

import java.util.List;

public interface UserService {
    List<UserInfoResponse> findAllUsers();

    UserInfoResponse createUser(CreateUserRequest request);
}
