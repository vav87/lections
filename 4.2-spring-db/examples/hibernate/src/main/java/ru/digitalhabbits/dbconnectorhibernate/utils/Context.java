package ru.digitalhabbits.dbconnectorhibernate.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import static ru.digitalhabbits.dbconnectorhibernate.utils.Status.SUCCESS;

@Accessors(fluent = true)
@AllArgsConstructor
@Builder
public class Context<T> {
    @Getter
    private final Status status;
    @Getter
    private final T entity;
    @Getter
    private final String problem;

    public Context (T entity) {
        this.status = SUCCESS;
        this.entity = entity;
        this.problem = null;
    }
}
