package ru.digitalhabbits.istio.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookInfoRequest {
    private String name;
    private Integer rating;
    private String author;
}
