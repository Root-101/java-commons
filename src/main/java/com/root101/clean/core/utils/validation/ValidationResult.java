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
package com.root101.clean.core.utils.validation;

import com.root101.clean.core.exceptions.ValidationException;
import com.root101.clean.core.utils.validation.checkables.Checkable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @author jjhurtado@Github
 */
public class ValidationResult {

    private final List<ValidationMessage> messages = new ArrayList<>();

    public ValidationResult() {
    }

    public List<ValidationMessage> getMessages() {
        return new ArrayList<>(messages);
    }

    public void check(Checkable checker, String messageOnError) {
        check(checker, ValidationMessage.from(checker.getSource(), messageOnError));
    }

    public void check(Checkable checker, ValidationMessage messageOnError) {
        if (!checker.check()) {
            messages.add(messageOnError);
        }
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

    public void add(ValidationMessage validationMessage) {
        this.messages.add(validationMessage);
    }

    public void add(List<ValidationMessage> validationMessages) {
        this.messages.addAll(validationMessages);
    }

    public void add(ValidationResult validationResult) {
        this.messages.addAll(validationResult.getMessages());
    }

    @Override
    public String toString() {
        return messages.isEmpty() ? "" : messages.get(0).toString();
    }

    public void checkFromAnnotations(Object objectToCheck) {
        add(Validation.buildDefaultValidatorFactory().getValidator().validate(objectToCheck));
    }

    public void checkFromAnnotations(Object objectToCheck, String field) {
        add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(objectToCheck, field));
    }

    private void add(Set<ConstraintViolation<Object>> validate) {
        for (ConstraintViolation<Object> c : validate) {
            add(ValidationMessage.from(c.getPropertyPath().toString(), c.getInvalidValue(), c.getMessage()));
        }
    }

}
