package dev.root101.commons.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {
    EnumValidatorRegister_String.class,
    EnumValidatorRegister_ListOfString.class
})
public @interface EnumValidator {

    String message() default "Value is not present in enum list.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public String detailMessage() default "";

    public Class<? extends Enum<?>> target();
}
