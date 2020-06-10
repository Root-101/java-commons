/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.utils.validation;

import com.clean.core.exceptions.ValidationException;
import com.clean.core.utils.validation.checkables.Checkable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * JoBits
 *
 * @author Jorge
 *
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
        if (checker.check()) {
            messages.add(messageOnError);
        }
    }

    public boolean haveError() {
        return !messages.isEmpty();
    }
    
    public void throwException(){
        if (haveError()) {
            throw new ValidationException(this);
        }
    }
}
