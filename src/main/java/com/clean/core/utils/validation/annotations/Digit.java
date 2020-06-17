package com.clean.core.utils.validation.annotations;

import com.clean.core.utils.validation.annotations.registers.DigitRegister;
import com.clean.core.utils.Severity;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DigitRegister.class)
public @interface Digit {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public String detailMessage() default "";

}
