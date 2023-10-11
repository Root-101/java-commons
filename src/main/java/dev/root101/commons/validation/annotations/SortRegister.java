package dev.root101.commons.validation.annotations;

import java.util.Map;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SortRegister implements ConstraintValidator<Sort, Map<String, String>> {

    @Override
    public void initialize(Sort a) {
        ConstraintValidator.super.initialize(a);
    }

    @Override
    public boolean isValid(Map<String, String> sort, ConstraintValidatorContext cvc) {
        return sort == null || sort.isEmpty() || sort.values().stream().anyMatch((t) -> {
            return SortType.contain(t);
        });
    }

}
