package ru.digitalhabbits.sbt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.digitalhabbits.sbt.repository.TestDatabaseConfiguration;

@ActiveProfiles("db-test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(TestDatabaseConfiguration.class)
public class SimpleWebApplicationTest {

    @Test
    void applicationRun() {}
}
