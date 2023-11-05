
package com.example.bookstore.models.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorResponseMessages implements ResponseMessages {
    UNEXPECTED("unexpected", "Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND("not_found_%s", "%s can't find %s", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_REGISTERED("email_already_registered", "Email already registered", HttpStatus.CONFLICT),
    PASSWORD_INCORRECT("password_incorrect", "Password is incorrect", HttpStatus.UNAUTHORIZED),
    PERMISSION_ERROR("permission", "permission  is forbidden", HttpStatus.FORBIDDEN),
    BEARER_TOKEN("unknown", "unknown authorized", HttpStatus.UNAUTHORIZED),
    CONFLICT("wrong data", "data is wrong", HttpStatus.CONFLICT);

    String key;
    String message;
    HttpStatus status;

    @Override
    public String key() {
        return key;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public HttpStatus status() {
        return status;
    }
}
