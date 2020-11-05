package ru.digitalhabbits.sbt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.digitalhabbits.sbt.domain.User;

public interface UserRepository
        extends JpaRepository<User, Integer> {}
