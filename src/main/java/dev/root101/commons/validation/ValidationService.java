package dev.root101.commons.validation;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import dev.root101.commons.exceptions.ValidationException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import jakarta.validation.Configuration;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.MessageInterpolator;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.util.ClassUtils;

public class ValidationService {

    public static final String PARENT_TREE_SEPARATOR = ".";

    public record TracedViolation(
            Set<ConstraintViolation<Object>> currentViolations,
            String parentTree) {

    }

    //store the config
    private static final Configuration<?> CONFIG = Validation.byDefaultProvider().configure();

    //Created static to avoid recreated every time a validation occur
    private static Validator DEFAULT_VALIDATOR = CONFIG.buildValidatorFactory().getValidator();

    private static PropertyNamingStrategies.NamingBase NAMING_STRATEGY;

    public static MessageInterpolator defaultMessageInterpolator() {
        return CONFIG.getDefaultMessageInterpolator();
    }

    public static void setDefaultNamingStrategy(PropertyNamingStrategies.NamingBase namingStrategy) {
        NAMING_STRATEGY = namingStrategy;
    }

    public static void setMessageInterpolator(MessageInterpolator msgInterp) {
        DEFAULT_VALIDATOR = CONFIG.messageInterpolator(
                msgInterp
        ).buildValidatorFactory().getValidator();
    }

    public static void validateAndThrow(Object... objects) throws ValidationException {
        List<TracedViolation> errors = validate(objects);
        if (!errors.isEmpty()) {
            throw new ValidationException(convertMessages(errors));
        }
    }

    public static List<TracedViolation> validate(Object... objects) {
        List<TracedViolation> errors = new ArrayList<>();

        if (objects.length == 1) {
            Set<ConstraintViolation<Object>> violations = DEFAULT_VALIDATOR.validate(objects[0]);
            if (!violations.isEmpty()) {
                errors.add(
                        new TracedViolation(
                                violations,
                                ""
                        )
                );
            }
        } else {
            for (int i = 0; i < objects.length; i++) {
                Set<ConstraintViolation<Object>> violations = DEFAULT_VALIDATOR.validate(objects[i]);
                if (!violations.isEmpty()) {
                    errors.add(
                            new TracedViolation(
                                    violations,
                                    "[%s]".formatted(i)
                            )
                    );
                }
            }
        }

        return errors;
    }

    public static void validateRecursiveAndThrow(Object... objects) throws ValidationException {
        List<TracedViolation> errors = validateRecursive(objects);
        if (!errors.isEmpty()) {
            throw new ValidationException(convertMessages(errors));
        }
    }

    public static List<TracedViolation> validateRecursive(Object... objects) {
        if (objects.length == 1) {
            return validateRecursive(new ArrayList<>(), "", objects[0]);
        } else {
            List<TracedViolation> errors = new ArrayList<>();
            for (int i = 0; i < objects.length; i++) {
                errors.addAll(
                        validateRecursive(
                                new ArrayList<>(),
                                "[%s]".formatted(i),//se le agrega el root en la conversion
                                objects[i]
                        )
                );
            }
            return errors;
        }
    }

    public static List<ValidationException.ValidationErrorMessage> convertMessages(List<TracedViolation> violation) {
        List<ValidationException.ValidationErrorMessage> messages = new ArrayList<>();
        for (TracedViolation general : violation) {
            if (!general.currentViolations.isEmpty()) {
                for (ConstraintViolation<Object> viol : general.currentViolations) {

                    String fieldName = viol.getPropertyPath().toString();
                    try {
                        Field field = viol.getRootBeanClass().getDeclaredField(fieldName);
                        ValidationFieldName fieldNameAnnotation = field.getDeclaredAnnotation(ValidationFieldName.class);
                        if (fieldNameAnnotation != null) {
                            fieldName = fieldNameAnnotation.value();
                        } else {
                            fieldName = globalParse(fieldName);
                        }

                    } catch (NoSuchFieldException | SecurityException e) {
                        System.out.println("Error convirtiendo los mensajes de las validaciones. " + e.getMessage());
                    }

                    String source = general.parentTree + PARENT_TREE_SEPARATOR + fieldName;
                    if (source.startsWith(PARENT_TREE_SEPARATOR)) {
                        source = source.substring(1, source.length());
                    } else if (source.startsWith("[")) {
                        source = "root" + source;
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
        }
        return messages;
    }

    private static List<TracedViolation> validateRecursive(List<TracedViolation> violations, String parentTree, Object object) {
        //si NO es null lo proceso. Null no tiene sentido validarlo(DEFAULT_VALIDATOR.validate(null) lanza excepcion), se deberia haber validado una capa arriba.
        if (object != null) {
            //si no es null compruebo si:
            if (object instanceof Object[] arr) {
                //es una instancia de arreglo, llamo a la recursividad con el arreglo
                for (int i = 0; i < arr.length; i++) {
                    validateRecursive(violations, parentTree + "[%s]".formatted(i), arr[i]);
                }
            } else if (object instanceof List list) {
                //es una instancia de lista, la convierto en arreglo y llamo a la recursividad con el arreglo
                for (int i = 0; i < list.size(); i++) {
                    validateRecursive(violations, parentTree + "[%s]".formatted(i), list.get(i));
                }
            } else {
                //no es ni una lista ni un arreglo, valido el objeto como objeto
                Set<ConstraintViolation<Object>> validations = DEFAULT_VALIDATOR.validate(object);
                if (!validations.isEmpty()) {
                    violations.add(
                            new TracedViolation(
                                    validations,
                                    parentTree
                            )
                    );
                }

                //luego recorro todos sus campos a ver si alguno no es primitivo
                Field fields[] = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    try {
                        field.setAccessible(true);
                        Object t = field.get(object);
                        if (t == null) {
                            continue;
                        }
                        Class tClass = t.getClass();

                        if (!(ClassUtils.isPrimitiveOrWrapper(tClass)
                                || tClass.getName().startsWith("java") || Enum.class.isAssignableFrom(tClass))
                                || List.class.isAssignableFrom(tClass)) {

                            String fieldName = field.getName();

                            ValidationFieldName fieldNameAnnotation = field.getDeclaredAnnotation(ValidationFieldName.class);
                            if (fieldNameAnnotation != null) {
                                fieldName = fieldNameAnnotation.value();
                            } else {
                                fieldName = globalParse(fieldName);
                            }

                            //de todos los campos que no son primitivos los valido
                            validateRecursive(violations, parentTree + PARENT_TREE_SEPARATOR + fieldName, t);
                        }
                    } catch (Exception e) {
                        System.out.println("Nunca debe entrar aqui, si entra IGNORAR, es un problema de acceso a los fields del objeto. " + e.getMessage());
                    }

                }
            }
        }
        return violations;
    }

    private static String globalParse(String fieldName) {
        if (NAMING_STRATEGY != null) {
            return NAMING_STRATEGY.translate(fieldName);
        }
        return fieldName;
    }

}
