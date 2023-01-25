package dev.root101.clean.core.utils.validation;

import dev.root101.clean.core.exceptions.ValidationException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.util.ClassUtils;

public class ValidationService {

    public record TreeTracedViolation(
            Set<ConstraintViolation<Object>> currentViolations,
            String parentTree) {

    }
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

    public static void validateAndThrow(Object... objects) throws ValidationException {
        List<TreeTracedViolation> errors = validate(objects);
        if (!errors.isEmpty()) {
            throw new ValidationException(convertMessages(errors));
        }
    }

    public static List<TreeTracedViolation> validate(Object... objects) {
        List<TreeTracedViolation> errors = new ArrayList<>();

        for (Object object : objects) {
            errors.add(new TreeTracedViolation(
                            DEFAULT_VALIDATOR.validate(object),
                            ""
                    )
            );
        }

        return errors;
    }

    public static void validateRecursiveAndThrow(Object... objects) throws ValidationException {
        List<TreeTracedViolation> errors = validateRecursive(objects);
        if (!errors.isEmpty()) {
            throw new ValidationException(convertMessages(errors));
        }
    }

    public static List<TreeTracedViolation> validateRecursive(Object... objects) {
        return validateRecursive(new ArrayList<>(), "", objects);
    }

    public static List<ValidationException.ValidationErrorMessage> convertMessages(List<TreeTracedViolation> violation) {
        List<ValidationException.ValidationErrorMessage> messages = new ArrayList<>();
        for (TreeTracedViolation general : violation) {
            for (ConstraintViolation<Object> viol : general.currentViolations) {

                String fieldName = viol.getPropertyPath().toString();
                try {
                    Field field = viol.getRootBeanClass().getDeclaredField(fieldName);
                    ValidationFieldName fieldNameAnnotation = field.getDeclaredAnnotation(ValidationFieldName.class);
                    if (fieldNameAnnotation != null) {
                        fieldName = fieldNameAnnotation.value();
                    }
                } catch (NoSuchFieldException | SecurityException e) {
                    System.out.println("Error convirtiendo los mensajes de las validaciones. " + e.getMessage());
                }

                String source = general.parentTree + "." + fieldName;
                if (source.startsWith(".")) {
                    source = source.substring(1, source.length());
                }

                messages.add(
                        new ValidationException.ValidationErrorMessage(
                                source,
                                viol.getInvalidValue() == null
                                ? "null"
                                : viol.getInvalidValue().toString(),
                                viol.getMessage()
                        )
                );

            }
        }
        return messages;
    }

    private static List<TreeTracedViolation> validateRecursive(List<TreeTracedViolation> violations, String parentTree, Object... objects) {
        //recorro la lista de objetos a validar
        for (Object object : objects) {
            //si NO es null lo proceso. Null no tiene sentido validarlo(DEFAULT_VALIDATOR.validate(null) lanza excepcion), se deberia haber validado una capa arriba.
            if (object != null) {
                //si no es null compruebo si:
                if (object instanceof Object[] arr) {
                    //es una instancia de arreglo, llamo a la recursividad con el arreglo
                    for (int i = 0; i < arr.length; i++) {
                        validateRecursive(violations, parentTree + "[%s]".formatted(i), arr);
                    }
                } else if (object instanceof List list) {
                    //es una instancia de lista, la convierto en arreglo y llamo a la recursividad con el arreglo
                    for (int i = 0; i < list.size(); i++) {
                        validateRecursive(violations, parentTree + "[%s]".formatted(i), list.toArray());
                    }
                } else {
                    //no es ni una lista ni un arreglo, valido el objeto como objeto
                    violations.add(new TreeTracedViolation(
                                    DEFAULT_VALIDATOR.validate(object),
                                    parentTree
                            )
                    );

                    //luego recorro todos sus campos a ver si alguno no es primitivo
                    Field fields[] = object.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        try {
                            Object t = field.get(object);

                            if (t != null
                                    && (!(ClassUtils.isPrimitiveOrWrapper(t.getClass()) || t.getClass().getName().startsWith("java"))
                                    || List.class.isAssignableFrom(t.getClass()))) {

                                String fieldName = field.getName();

                                ValidationFieldName fieldNameAnnotation = field.getDeclaredAnnotation(ValidationFieldName.class);
                                if (fieldNameAnnotation != null) {
                                    fieldName = fieldNameAnnotation.value();
                                }

                                //de todos los campos que no son promitivos los valido
                                validateRecursive(violations, parentTree + "." + fieldName, t);
                            }
                        } catch (IllegalAccessException | IllegalArgumentException e) {
                            System.out.println("Nunca debe entrar aqui");
                        }

                    }
                }
            }
        }
        return violations;
    }
}
