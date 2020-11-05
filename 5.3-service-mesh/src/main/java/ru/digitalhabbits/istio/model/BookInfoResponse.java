package ru.digitalhabbits.istio.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class BookInfoResponse {
    private UUID uid;
    private String name;
    private Integer rating;
    private String author;
}
