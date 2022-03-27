/*
 * Copyright 2021 Root101 (jhernandezb96@gmail.com, +53-5-426-8660).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Or read it directly from LICENCE.txt file at the root of this project.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.root101.clean.core.utils.validation;

import dev.root101.clean.core.app.domain.services.ResourceHandler;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @author jjhurtado@Github
 */
public class ValidationMessage {

    public static final String ENCLOSING_STRING = "#";
    public static final String SPLIT_STRING = "~";

    private final String source;
    private final Object value;
    private final String message;
    private final String detailMessage;

    private ValidationMessage(String source, Object value, String message, String detailMessage) {
        this.source = source;
        this.value = value;
        this.message = unwrapString(message);
        this.detailMessage = unwrapString(detailMessage);
    }

    public static ValidationMessage from(String source, String message) {
        String split[] = message.split(SPLIT_STRING);
        String messageSimple = split[0];
        String messageDetail = split.length >= 2 ? split[1] : messageSimple;
        return new ValidationMessage(source, null, messageSimple, messageDetail);
    }

    public static ValidationMessage from(String source, Object value, String message) {
        String split[] = message.split(SPLIT_STRING);
        String messageSimple = split[0];
        String messageDetail = split.length >= 2 ? split[1] : messageSimple;
        return new ValidationMessage(source, value, messageSimple, messageDetail);
    }

    public static ValidationMessage from(String source, String messages, String detailMessages) {
        return new ValidationMessage(source, null, messages, detailMessages);
    }

    public String getMessage() {
        return message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public String getSource() {
        return source;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return detailMessage;
    }

    public static builder builder() {
        return new builder();
    }

    public static class builder {

        private String source;
        private Object value;
        private String message;
        private String detailMessage;

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

        public ValidationMessage build() {
            if (detailMessage == null) {
                detailMessage = message;
            }
            return new ValidationMessage(source, value, message, detailMessage);
        }
    }

    public static String msgFromResource(String text) {
        return ENCLOSING_STRING + text + ENCLOSING_STRING;
    }

    private String unwrapString(String message) {
        if (message.startsWith(ENCLOSING_STRING) && message.endsWith(ENCLOSING_STRING)) {
            String keyCleaned = message.substring(ENCLOSING_STRING.length(), message.length() - ENCLOSING_STRING.length());
            return ResourceHandler.getString(keyCleaned);
        } else {
            return message;
        }
    }
}
