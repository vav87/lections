package ru.digitalhabbits.sbt.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfoResponse {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer age;
}
