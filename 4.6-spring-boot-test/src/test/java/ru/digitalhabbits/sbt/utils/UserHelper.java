package ru.digitalhabbits.sbt.utils;

import ru.digitalhabbits.sbt.model.CreateUserRequest;
import ru.digitalhabbits.sbt.model.UserInfoResponse;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class UserHelper {

    public static CreateUserRequest buildCreateUserRequest() {
        return new CreateUserRequest()
                .setAge(nextInt(10, 50))
                .setFirstName(randomAlphabetic(8))
                .setMiddleName(randomAlphabetic(8))
                .setLastName(randomAlphabetic(8));
    }

    public static UserInfoResponse buildUserInfoResponse() {
        return new UserInfoResponse()
                .setId(nextInt())
                .setAge(nextInt(10, 50))
                .setFirstName(randomAlphabetic(8))
                .setMiddleName(randomAlphabetic(8))
                .setLastName(randomAlphabetic(8));
    }

    public static UserInfoResponse buildUserInfoResponse(CreateUserRequest request) {
        return new UserInfoResponse()
                .setId(nextInt())
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAge(request.getAge());
    }
}
