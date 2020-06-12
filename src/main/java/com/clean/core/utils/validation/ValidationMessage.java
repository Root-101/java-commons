package com.clean.core.utils.validation;

import com.clean.core.utils.Severity;

/**
 * 
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class ValidationMessage {

    private final Object source;
    private final String message;
    private final Severity severity;

    private ValidationMessage(Object source, String message, Severity severity) {
        this.source = source;
        this.message = message;
        this.severity = severity;
    }

    public static ValidationMessage from(Object source, String messages) {
        return new ValidationMessage(source, messages, Severity.WARNING);
    }

    public static ValidationMessage from(Object source, String messages, Severity severity) {
        return new ValidationMessage(source, messages, severity);
    }

    public static ValidationMessage from(DefaultMessage messages) {
        throw new UnsupportedOperationException();
    }

    public String getMessage() {
        return message;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Object getSource() {
        return source;
    }

}
