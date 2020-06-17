package com.clean.core.utils.validation.annotations;

import com.clean.core.utils.validation.ValidationMessage;
import com.clean.core.utils.validation.ValidationResult;
import com.clean.core.utils.validation.checkables.Checkable;
import com.clean.core.utils.validation.checkables.CheckerFactory;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class DigitRegister implements ConstraintValidator<Digit, Character> {

    @Override
    public void initialize(Digit a) {
        ConstraintValidator.super.initialize(a);
    }

    @Override
    public boolean isValid(Character t, ConstraintValidatorContext cvc) {
        return CheckerFactory.buildDigitCheckable(t).check();
    }

}
