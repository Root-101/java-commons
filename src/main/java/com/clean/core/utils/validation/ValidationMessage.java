package com.clean.core.utils.validation;

import com.clean.core.utils.Severity;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class ValidationMessage {

    public static final String ENCLOSING_STRING = "#";

    private final Object source;
    private final String message;
    private final String detailMessage;
    private final Severity severity;

    private ValidationMessage(Object source, String message, Severity severity) {
        this(source, message, message, severity);
    }

    private ValidationMessage(Object source, String message, String detailMessage, Severity severity) {
        this.source = source;
        this.message = unwrapString(message);
        this.detailMessage = unwrapString(detailMessage);
        this.severity = severity;
    }

    public static ValidationMessage from(Object source, String message) {
        return new ValidationMessage(source, message, message, Severity.WARNING);
    }

    public static ValidationMessage from(Object source, String messages, Severity severity) {
        return new ValidationMessage(source, messages, severity);
    }

    public static ValidationMessage from(Object source, String messages, String detailMessages, Severity severity) {
        return new ValidationMessage(source, messages, detailMessages, severity);
    }

    public static ValidationMessage from(DefaultMessage messages) {
        throw new UnsupportedOperationException();
    }

    public String getMessage() {
        return message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Object getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "ValidationMessage{" + "source=" + source + ", message=" + message + ", detailMessage=" + detailMessage + ", severity=" + severity + '}';
    }

    public static builder builder() {
        return new builder();
    }

    public static class builder {

        private Object source;
        private String message;
        private String detailMessage;
        private Severity severity = Severity.WARNING;

        public builder source(Object source) {
            this.source = source;
            return this;
        }

        public builder message(String message) {
            this.message = message;
            return this;
        }

        public builder detailMessage(String detailMessage) {
            this.detailMessage = detailMessage;
            return this;
        }

        public builder source(Severity severity) {
            this.severity = severity;
            return this;
        }

        public ValidationMessage build() {
            if (detailMessage == null) {
                detailMessage = message;
            }
            return new ValidationMessage(source, message, detailMessage, severity);
        }
    }

    private String unwrapString(String message) {
        if (message.startsWith(ENCLOSING_STRING) && message.endsWith(ENCLOSING_STRING)) {
            throw new UnsupportedOperationException("Developing");
        } else {
            return message;
        }
    }
}
