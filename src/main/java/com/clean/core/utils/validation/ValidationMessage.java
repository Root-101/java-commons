package com.clean.core.utils.validation;

import com.clean.core.domain.services.Resource;
import com.clean.core.utils.Severity;

/**
 *
 * @author Jorge
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class ValidationMessage {

    public static final String ENCLOSING_STRING = "#";
    public static final String SPLIT_STRING = "~";

    private final String source;
    private final Object value;
    private final String message;
    private final String detailMessage;
    private final Severity severity;

    private ValidationMessage(String source, Object value, String message, String detailMessage, Severity severity) {
        this.source = source;
        this.value = value;
        this.message = unwrapString(message);
        this.detailMessage = unwrapString(detailMessage);
        this.severity = severity;
    }

    public static ValidationMessage from(String source, String message) {
        String split[] = message.split(SPLIT_STRING);
        String messageSimple = split[0];
        String messageDetail = split.length >= 2 ? split[1] : messageSimple;
        return new ValidationMessage(source, null, messageSimple, messageDetail, Severity.WARNING);
    }

    public static ValidationMessage from(String source, Object value, String message) {
        String split[] = message.split(SPLIT_STRING);
        String messageSimple = split[0];
        String messageDetail = split.length >= 2 ? split[1] : messageSimple;
        return new ValidationMessage(source, value, messageSimple, messageDetail, Severity.WARNING);
    }

    public static ValidationMessage from(String source, String messages, String detailMessages) {
        return new ValidationMessage(source, null, messages, detailMessages, Severity.WARNING);
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

    public String getSource() {
        return source;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return message;
    }

    public static builder builder() {
        return new builder();
    }

    public static class builder {

        private String source;
        private Object value;
        private String message;
        private String detailMessage;
        private Severity severity = Severity.WARNING;

        public builder source(String source) {
            this.source = source;
            return this;
        }

        public builder value(Object value) {
            this.value = value;
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

        public builder severity(Severity severity) {
            this.severity = severity;
            return this;
        }

        public ValidationMessage build() {
            if (detailMessage == null) {
                detailMessage = message;
            }
            return new ValidationMessage(source, value, message, detailMessage, severity);
        }
    }

    public static String msgFromResource(String text) {
        return ENCLOSING_STRING + text + ENCLOSING_STRING;
    }

    private String unwrapString(String message) {
        if (message.startsWith(ENCLOSING_STRING) && message.endsWith(ENCLOSING_STRING)) {
            String keyCleaned = message.substring(ENCLOSING_STRING.length(), message.length() - ENCLOSING_STRING.length());
            return Resource.getString(keyCleaned);
        } else {
            return message;
        }
    }
}
