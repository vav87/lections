package ru.dhabits.docker.model;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ru.dhabits.user")
@TypeAlias("UserEntity")
public class UserEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;

    public UserEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserEntity user = (UserEntity) o;
        return Objects.equals(id, user.id) &&
            Objects.equals(firstName, user.firstName) &&
            Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
    }
}
