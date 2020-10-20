package ru.digitalhabbits.dbconnectorjdbc.dto;

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

    public BaseResponse(String text) {
        this.text = text;
        this.dto = null;
    }

    public BaseResponse(T dto) {
        this.text = "Запрос выполнен успешно";
        this.dto = dto;
    }
}
