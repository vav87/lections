package ru.digitalhabbits.sbt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabbits.sbt.domain.User;
import ru.digitalhabbits.sbt.model.CreateUserRequest;
import ru.digitalhabbits.sbt.model.EventType;
import ru.digitalhabbits.sbt.model.UserInfoResponse;
import ru.digitalhabbits.sbt.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional(readOnly = true)
    public List<UserInfoResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::buildUserInfoResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserInfoResponse createUser(CreateUserRequest request) {
        User user = new User()
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAge(request.getAge());

        user = userRepository.save(user);
        notificationService.notify(EventType.CREATED, user.toString());

        return buildUserInfoResponse(user);
    }

    private UserInfoResponse buildUserInfoResponse(User user) {
        return new UserInfoResponse()
                .setId(user.getId())
                .setFirstName(user.getFirstName())
                .setMiddleName(user.getMiddleName())
                .setLastName(user.getLastName())
                .setAge(user.getAge());
    }
}
