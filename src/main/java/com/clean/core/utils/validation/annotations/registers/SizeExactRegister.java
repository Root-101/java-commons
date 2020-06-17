package com.clean.core.utils.validation.annotations.registers;

import com.clean.core.utils.validation.annotations.SizeExact;
import com.clean.core.utils.validation.checkables.CheckerFactory;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class SizeExactRegister implements ConstraintValidator<SizeExact, String> {

    private int length;

    @Override
    public void initialize(SizeExact a) {
        ConstraintValidator.super.initialize(a);
        this.length = a.length();
    }

    @Override
    public boolean isValid(String text, ConstraintValidatorContext cvc) {
        return CheckerFactory.buildLengthExactCheckable(text, length).check();
    }

}
