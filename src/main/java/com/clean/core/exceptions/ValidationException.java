package com.clean.core.exceptions;

import com.clean.core.utils.validation.ValidationResult;
import com.clean.core.utils.validation.checkables.CheckerFactory;

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

    public ValidationException(Object source, String string) {
        super(string);
        this.validationErrors = new ValidationResult();
        this.validationErrors.check(CheckerFactory.buildNeverCheckable(source), string);
    }

    public ValidationException(ValidationResult validationErrors) {
        this.validationErrors = validationErrors;
    }

    @Override
    public String getMessage() {
        return validationErrors.toString();
    }
}
