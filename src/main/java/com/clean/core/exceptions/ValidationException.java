package com.clean.core.exceptions;

import com.clean.core.utils.validation.ValidationMessage;
import com.clean.core.utils.validation.ValidationResult;

/**
 *
 * @author Jorge
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class ValidationException extends IllegalArgumentException {

    private final ValidationResult validationErrors;

    public ValidationException(String string) {
        this(null, string);
    }

    public ValidationException(String source, String message) {
        super(message);
        this.validationErrors = new ValidationResult();
        this.validationErrors.add(ValidationMessage.from(source, message));
    }

    public ValidationException(ValidationResult validationErrors) {
        this.validationErrors = validationErrors;
    }

    public ValidationResult getValidationErrors() {
        return validationErrors;
    }

    @Override
    public String getMessage() {
        return validationErrors.toString();
    }
}
