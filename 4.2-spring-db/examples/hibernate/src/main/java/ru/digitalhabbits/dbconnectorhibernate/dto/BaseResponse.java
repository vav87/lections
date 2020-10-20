package ru.digitalhabbits.dbconnectorhibernate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@AllArgsConstructor
@Builder
public final class BaseResponse<T> {
    @Getter
    private final String text;
    @Getter
    @JsonProperty("result")
    private final T dto;
}
