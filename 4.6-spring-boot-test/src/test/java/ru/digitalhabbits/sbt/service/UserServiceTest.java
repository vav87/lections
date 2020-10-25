package ru.digitalhabbits.sbt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.digitalhabbits.sbt.domain.User;
import ru.digitalhabbits.sbt.model.CreateUserRequest;
import ru.digitalhabbits.sbt.model.UserInfoResponse;
import ru.digitalhabbits.sbt.repository.UserRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.digitalhabbits.sbt.utils.UserHelper.buildCreateUserRequest;

@ActiveProfiles("service-test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserServiceConfiguration.class, MockRepositoriesConfiguration.class })
class UserServiceTest {
    private static final int COUNT = 2;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllUsers() {
        when(userRepository.findAll())
                .thenReturn(range(0, COUNT).mapToObj(i -> buildUser()).collect(toList()));

        final List<UserInfoResponse> users = userService.findAllUsers();

        assertEquals(COUNT, users.size());
    }

    @Test
    void createUser() {
        final CreateUserRequest request = buildCreateUserRequest();
        final User user = new User()
                .setId(nextInt())
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAge(request.getAge());

        when(userRepository.save(any(User.class))).thenReturn(user);

        final UserInfoResponse response = userService.createUser(request);
        assertEquals(request.getFirstName(), response.getFirstName());
        assertEquals(request.getMiddleName(), response.getMiddleName());
        assertEquals(request.getLastName(), response.getLastName());
        assertEquals(request.getAge(), response.getAge());
    }

    private User buildUser() {
        return new User()
                .setFirstName(randomAlphabetic(7))
                .setMiddleName(randomAlphabetic(7))
                .setLastName(randomAlphabetic(7))
                .setAge(nextInt(10, 50));
    }
}