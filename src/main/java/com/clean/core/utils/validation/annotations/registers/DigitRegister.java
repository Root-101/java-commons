package com.clean.core.utils.validation.annotations.registers;

import com.clean.core.utils.validation.annotations.Digit;
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
    public boolean isValid(Character digit, ConstraintValidatorContext cvc) {
        return CheckerFactory.buildDigitCheckable(digit).check();
    }

}
