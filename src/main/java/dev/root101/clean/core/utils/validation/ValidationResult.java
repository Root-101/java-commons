/*
 * Copyright 2022 Root101 (jhernandezb96@gmail.com, +53-5-426-8660).
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

import dev.root101.clean.core.exceptions.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @author jjhurtado@Github
 */
public class ValidationResult {

    //Created static to avoid recreated every time a validation occur
    private static Validator DEFAULT_VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    //in case some other Validator is used
    public static void setValidator(Validator validator) {
        DEFAULT_VALIDATOR = validator;
    }

    private final List<ValidationMessage> messages = new ArrayList<>();

    public static ValidationResult build() {
        return new ValidationResult();
    }

    public static ValidationResult validateForAnnotations(Object objectToCheck) {
        return build().checkFromAnnotations(objectToCheck);
    }

    private ValidationResult() {
    }

    public List<ValidationMessage> getMessages() {
        return new ArrayList<>(messages);
    }

    public boolean haveError() {
        return !messages.isEmpty();
    }

    public ValidationResult throwException() {
        if (haveError()) {
            throw new ValidationException(this);
        } else {
            return this;
        }
    }

    public ValidationResult checkFromAnnotations(Object objectToCheck) {
        add(DEFAULT_VALIDATOR.validate(objectToCheck));
        return this;
    }

    public ValidationResult checkFromAnnotations(Object objectToCheck, String field) {
        add(DEFAULT_VALIDATOR.validateProperty(objectToCheck, field));
        return this;
    }

    public void add(ValidationMessage validationMessage) {
        this.messages.add(validationMessage);
    }

    public void add(List<ValidationMessage> validationMessages) {
        this.messages.addAll(validationMessages);
    }

    public void add(ValidationResult validationResult) {
        this.messages.addAll(validationResult.getMessages());
    }

    private void add(Set<ConstraintViolation<Object>> validate) {
        for (ConstraintViolation<Object> c : validate) {
            add(ValidationMessage.from(c.getPropertyPath().toString(), c.getInvalidValue(), c.getMessage()));
        }
    }

    @Override
    public String toString() {
        return messages.isEmpty() ? "" : messages.get(0).toString();
    }

}
