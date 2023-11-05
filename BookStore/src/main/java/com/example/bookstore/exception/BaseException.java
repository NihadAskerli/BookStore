package com.example.bookstore.exception;

import com.example.bookstore.exception.types.NotFoundExceptionType;
import com.example.bookstore.models.response.ResponseMessages;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

import static com.example.bookstore.models.response.ErrorResponseMessages.NOT_FOUND;
import static com.example.bookstore.models.response.ErrorResponseMessages.UNEXPECTED;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseException extends RuntimeException {

    ResponseMessages responseMessage;
    NotFoundExceptionType notFoundData;

    // todo: fix. it doesn't return dynamic error message
    @Override
    public String getMessage() {
        return responseMessage.message();
    }

    public static BaseException of(ResponseMessages responseMessage) {
        return BaseException.builder().responseMessage(responseMessage).build();
    }
    public static BaseException unexpected() {
        return of(UNEXPECTED);
    }
    public static BaseException notFound(String target, String field, String value) {
        return BaseException.builder()
                .responseMessage(NOT_FOUND)
                .notFoundData(
                        NotFoundExceptionType.of(target, Map.of(field, value))
                )
                .build();
    }

}
