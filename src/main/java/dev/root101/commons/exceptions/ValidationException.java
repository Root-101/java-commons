package dev.root101.commons.exceptions;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;

public class ValidationException extends ApiException {

    private final static HttpStatus STATUS_CODE = HttpStatus.UNPROCESSABLE_ENTITY;
    private List<ValidationErrorMessage> messages;

    public ValidationException(ValidationErrorMessage message) {
        this(List.of(message));
    }

    public ValidationException(List<ValidationErrorMessage> messages) {
        super(STATUS_CODE);
        this.messages = messages;
    }

    public HttpStatus getStatusCode() {
        return STATUS_CODE;
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
            String message) {

    }

    @Override
    public String toString() {
        return "ValidationException{" + "statusCode=" + STATUS_CODE + ", messages=" + Arrays.toString(messages.toArray()) + '}';
    }
}
