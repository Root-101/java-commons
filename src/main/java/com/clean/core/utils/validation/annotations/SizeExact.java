package com.clean.core.utils.validation.annotations;

import com.clean.core.utils.validation.annotations.registers.SizeExactRegister;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SizeExactRegister.class)
public @interface SizeExact {

    int length();

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public String detailMessage() default "";

}
