package com.clean.core.utils.validation;

import com.clean.core.exceptions.ValidationException;
import com.clean.core.utils.validation.checkables.Checkable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 * @author Jorge
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
        return messages.toString();
    }

    public void check(Object object) {
        add(Validation.buildDefaultValidatorFactory().getValidator().validate(object));
    }

    private void add(Set<ConstraintViolation<Object>> validate) {
        for (ConstraintViolation<Object> c : validate) {
            add(ValidationMessage.from(c.getInvalidValue(), c.getMessage()));
        }
    }

}
