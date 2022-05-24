package dev.root101.clean.core.exceptions;

import java.util.List;
import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {

    private final HttpStatus statusCode = HttpStatus.UNPROCESSABLE_ENTITY;
    private List<ValidationErrorMessage> messages;

    public ValidationException(List<ValidationErrorMessage> messages) {
        this.messages = messages;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public List<ValidationErrorMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ValidationErrorMessage> messages) {
        this.messages = messages;
    }

    public record ValidationErrorMessage(
            String source,
            String invalid_value,
            String mensaje) {

    }
}
