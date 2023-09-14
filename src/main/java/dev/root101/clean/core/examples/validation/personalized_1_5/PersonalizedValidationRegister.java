package dev.root101.clean.core.examples.validation.personalized_1_5;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PersonalizedValidationRegister implements ConstraintValidator<PersonalizedValidation, String> {

    private PersonalizedValidation annotation;

    @Override
    public void initialize(PersonalizedValidation annotation) {
        ConstraintValidator.super.initialize(annotation);
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String target, ConstraintValidatorContext cvc) {
        boolean valid = target != null && target.equalsIgnoreCase(annotation.name());

        if (!valid) {
            cvc.disableDefaultConstraintViolation();
            cvc.buildConstraintViolationWithTemplate(
                    String.format(
                            "'%s' don't match with given name".formatted(
                                    target,
                                    annotation.name()
                            )
                    )
            ).addConstraintViolation();
        }

        return valid;
    }

}
