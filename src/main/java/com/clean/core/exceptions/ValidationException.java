package com.clean.core.exceptions;

import com.clean.core.utils.validation.ValidationResult;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 * @author Jorge
 */
public class ValidationException extends IllegalArgumentException {

    public ValidationException() {
    }

    public ValidationException(String string) {
        super(string);
    }
    
    
    public ValidationException(ValidationResult validationErrors) {
        throw new UnsupportedOperationException(); //To change body of generated methods, choose Tools | Templates.
    }

}
