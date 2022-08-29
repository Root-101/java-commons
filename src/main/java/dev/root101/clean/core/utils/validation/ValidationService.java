package dev.root101.clean.core.utils.validation;

import dev.root101.clean.core.exceptions.ValidationException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.util.ClassUtils;

public class ValidationService {

    //store the config
    private static final Configuration<?> CONFIG = Validation.byDefaultProvider().configure();

    //Created static to avoid recreated every time a validation occur
    private static Validator DEFAULT_VALIDATOR = CONFIG.buildValidatorFactory().getValidator();

    public static MessageInterpolator defaultMessageInterpolator() {
        return CONFIG.getDefaultMessageInterpolator();
    }

    public static void setMessageInterpolator(MessageInterpolator msgInterp) {
        DEFAULT_VALIDATOR = CONFIG.messageInterpolator(
                msgInterp
        ).buildValidatorFactory().getValidator();
    }

    public static List<ConstraintViolation<Object>> validate(Object... objects) {
        List<ConstraintViolation<Object>> errors = new ArrayList<>();

        for (Object object : objects) {
            Set<ConstraintViolation<Object>> aaa = DEFAULT_VALIDATOR.validate(object);
            errors.addAll(aaa);
        }

        return errors;
    }

    public static List<ConstraintViolation<Object>> validateRecursive(Object... objects) {
        return validateRecursive(new ArrayList<>(), objects);
    }

    public static void validateAndThrow(Object... objects) throws ValidationException {
        List<ConstraintViolation<Object>> errors = validate(objects);
        if (!errors.isEmpty()) {
            throw new ValidationException(convertMessages(errors));
        }
    }

    public static void validateRecursiveAndThrow(Object... objects) throws ValidationException {
        List<ConstraintViolation<Object>> errors = validateRecursive(objects);
        if (!errors.isEmpty()) {
            throw new ValidationException(convertMessages(errors));
        }
    }

    public static List<ValidationException.ValidationErrorMessage> convertMessages(List<ConstraintViolation<Object>> violation) {
        return violation.stream().map((viol) -> {
            return new ValidationException.ValidationErrorMessage(viol.getPropertyPath().toString(), viol.getInvalidValue() == null ? "null" : viol.getInvalidValue().toString(), viol.getMessage());
        }).collect(Collectors.toList());
    }

    private static List<ConstraintViolation<Object>> validateRecursive(List<ConstraintViolation<Object>> currentViolations, Object... objects) {
        //recorro la lista de objetos a validar
        for (Object object : objects) {
            //si NO es null lo proceso. Null no tiene sentido validarlo(DEFAULT_VALIDATOR.validate(null) lanza excepcion), se deberia haber validado una capa arriba.
            if (object != null) {
                //si no es null compruebo si:
                if (object instanceof Object[] arr) {
                    //es una instancia de arreglo, llamo a la recursividad con el arreglo
                    validateRecursive(currentViolations, arr);
                } else if (object instanceof List list) {
                    //es una instancia de lista, la convierto en arreglo y llamo a la recursividad con el arreglo
                    validateRecursive(currentViolations, list.toArray());
                } else {
                    //no es ni una lista ni un arreglo, valido el objeto como objeto
                    currentViolations.addAll(DEFAULT_VALIDATOR.validate(object));

                    //luego recorro todos sus campos a ver si alguno no es primitivo
                    Field fields[] = object.getClass().getDeclaredFields();
                    Object[] arr
                            = List.of(fields).stream()
                                    //convierto los field en el objeto como tal
                                    .map((field) -> {
                                        field.setAccessible(true);
                                        try {
                                            return field.get(object);
                                        } catch (IllegalAccessException | IllegalArgumentException e) {
                                            System.out.println("Nunca debe entrar aqui");
                                        }
                                        return null;
                                    })
                                    //filtro de todos los valores los que NO sean primitivos
                                    .filter((t) -> {
                                        return t != null && !(ClassUtils.isPrimitiveOrWrapper(t.getClass()) || t.getClass().getName().startsWith("java"));
                                    })
                                    .toArray();
                    //de todos los campos que no son promitivos los valido
                    validateRecursive(currentViolations, arr);
                }
            }
        }
        return currentViolations;
    }
}
