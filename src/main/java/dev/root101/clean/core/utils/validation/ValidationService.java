package dev.root101.clean.core.utils.validation;

import dev.root101.clean.core.exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

public class ValidationService {

    //Created static to avoid recreated every time a validation occur
    private static final Validator DEFAULT_VALIDATOR = javax.validation.Validation.buildDefaultValidatorFactory().getValidator();

    public static Set<ConstraintViolation<Object>> validate(Object object) {
        return DEFAULT_VALIDATOR.validate(object);
    }

    public static void validateAndThrow(Object... objects) throws ValidationException {
        List<ConstraintViolation<Object>> errors = new ArrayList<>();
        List.of(objects).forEach((object) -> {
            Set<ConstraintViolation<Object>> aaa = DEFAULT_VALIDATOR.validate(object);
            errors.addAll(aaa);
        });
        if (!errors.isEmpty()) {
            throw new ValidationException(convertMessages(errors));
        }
    }

    private static List<ValidationException.ValidationErrorMessage> convertMessages(List<ConstraintViolation<Object>> violation) {
        return violation.stream().map((viol) -> {
            return new ValidationException.ValidationErrorMessage(viol.getPropertyPath().toString(), viol.getInvalidValue().toString(), viol.getMessage());
        }).collect(Collectors.toList());
    }
}
