package dev.root101.clean.core.examples.validation.personalized_1_5;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PersonalizedValidationRegister.class)
@interface PersonalizedValidation {

    String name();

    String message() default "Names don't match.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public String detailMessage() default "";

}