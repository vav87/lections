package ru.digitalhabbits.istio.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Book {
    private UUID uid;
    private String name;
    private Integer rating;
    private String author;
}
