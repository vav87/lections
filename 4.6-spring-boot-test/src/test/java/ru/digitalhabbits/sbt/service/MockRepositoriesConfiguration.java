package ru.digitalhabbits.sbt.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.digitalhabbits.sbt.repository.UserRepository;

@TestConfiguration
@MockBean(UserRepository.class)
public class MockRepositoriesConfiguration {}
