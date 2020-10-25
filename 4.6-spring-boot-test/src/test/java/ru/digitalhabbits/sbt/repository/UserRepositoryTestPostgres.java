package ru.digitalhabbits.sbt.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.digitalhabbits.sbt.domain.User;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("db-test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTestPostgres {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository.saveAll(generateUsers(2));
    }

    @Test
    void testFindUsers() {
        final List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
    }

    private List<User> generateUsers(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new User()
                        .setFirstName(randomAlphabetic(7))
                        .setMiddleName(randomAlphabetic(7))
                        .setLastName(randomAlphabetic(7))
                        .setAge(nextInt(10, 50)))
                .collect(toList());
    }
}
