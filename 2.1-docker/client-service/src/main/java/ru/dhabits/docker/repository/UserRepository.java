package ru.dhabits.docker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dhabits.docker.model.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {

}
