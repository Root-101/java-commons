package dev.root101.commons.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    public record TracedViolation(
            Set<ConstraintViolation<Object>> currentViolations,
            String parentTree) {

    }

    public static final Configuration<?> DEFAULT_CONFIG = Validation.byDefaultProvider().configure();

    public static final String DEFAULT_PARENT_TREE_SEPARATOR = ".";

    private final String parentTeeSeparator;
    private final Validator validator;
    private final PropertyNamingStrategies.NamingBase namingStrategy;

    public ValidationService(String parentTeeSeparator, Validator validator, PropertyNamingStrategies.NamingBase namingStrategy) {
        this.parentTeeSeparator = parentTeeSeparator;
        this.validator = validator;
        this.namingStrategy = namingStrategy;
    }

    public static ValidationService basic() {
        return builder().build();
    }

    public static ValidationServiceBuilder builder() {
        return new ValidationServiceBuilder();
    }

    public void validateAndThrow(Object... objects) throws ValidationException {
        List<TracedViolation> errors = validate(objects);
        if (!errors.isEmpty()) {
            throw new ValidationException(convertMessages(errors));
        }
    }

    public List<TracedViolation> validate(Object... objects) {
        List<TracedViolation> errors = new ArrayList<>();

        if (objects.length == 1) {
            Set<ConstraintViolation<Object>> violations = validator.validate(objects[0]);
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
                Set<ConstraintViolation<Object>> violations = validator.validate(objects[i]);
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

    public void validateRecursiveAndThrow(Object... objects) throws ValidationException {
        List<TracedViolation> errors = validateRecursive(objects);
        if (!errors.isEmpty()) {
            throw new ValidationException(convertMessages(errors));
        }
    }

    public List<TracedViolation> validateRecursive(Object... objects) {
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

    public List<ValidationException.ValidationErrorMessage> convertMessages(List<TracedViolation> violation) {
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
                            JsonProperty jsonProp = field.getDeclaredAnnotation(JsonProperty.class);
                            if (jsonProp != null && jsonProp.value() != null) {
                                fieldName = jsonProp.value();
                            } else {
                                fieldName = parseFieldName(fieldName);
                            }
                        }

                    } catch (NoSuchFieldException | SecurityException e) {
                        System.out.println("Error convirtiendo los mensajes de las validaciones. " + e.getMessage());
                    }

                    String source = general.parentTree + parentTeeSeparator + fieldName;
                    if (source.startsWith(parentTeeSeparator)) {
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

    private List<TracedViolation> validateRecursive(List<TracedViolation> violations, String parentTree, Object object) {
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
                Set<ConstraintViolation<Object>> validations = validator.validate(object);
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
                        Object fieldOfObject = field.get(object);
                        if (fieldOfObject == null) {
                            continue;
                        }
                        Class classOfField = fieldOfObject.getClass();

                        if (!(ClassUtils.isPrimitiveOrWrapper(classOfField)
                                || classOfField.getName().startsWith("java") || Enum.class.isAssignableFrom(classOfField))
                                || List.class.isAssignableFrom(classOfField)) {

                            String fieldName = field.getName();

                            ValidationFieldName fieldNameAnnotation = field.getDeclaredAnnotation(ValidationFieldName.class);
                            if (fieldNameAnnotation != null) {
                                fieldName = fieldNameAnnotation.value();
                            } else {
                                JsonProperty jsonProp = field.getDeclaredAnnotation(JsonProperty.class);
                                if (jsonProp != null && jsonProp.value() != null) {
                                    fieldName = jsonProp.value();
                                } else {
                                    fieldName = parseFieldName(fieldName);
                                }
                            }

                            //de todos los campos que no son primitivos los valido
                            validateRecursive(violations, parentTree + parentTeeSeparator + fieldName, fieldOfObject);
                        }
                    } catch (Exception e) {
                        System.out.println("Nunca debe entrar aqui, si entra IGNORAR, es un problema de acceso a los fields del objeto. " + e.getMessage());
                    }

                }
            }
        }
        return violations;
    }

    private String parseFieldName(String fieldName) {
        if (namingStrategy != null) {
            return namingStrategy.translate(fieldName);
        }
        return fieldName;
    }

    public static class ValidationServiceBuilder {

        private String parentTeeSeparator;
        private Configuration<?> config;
        private Validator validator;
        private PropertyNamingStrategies.NamingBase namingStrategy;

        public ValidationServiceBuilder() {
            this.parentTeeSeparator = DEFAULT_PARENT_TREE_SEPARATOR;
            this.config = DEFAULT_CONFIG;
            this.validator = this.config.buildValidatorFactory().getValidator();
        }

        public ValidationServiceBuilder parentTeeSeparator(String parentTeeSeparator) {
            this.parentTeeSeparator = parentTeeSeparator;
            return this;
        }

        public ValidationServiceBuilder config(Configuration<?> config) {
            this.config = config;
            return this;
        }

        public ValidationServiceBuilder namingStrategy(PropertyNamingStrategies.NamingBase namingStrategy) {
            this.namingStrategy = namingStrategy;
            return this;
        }

        public ValidationServiceBuilder messageInterpolator(MessageInterpolator msgInterp) {
            this.validator = config.messageInterpolator(
                    msgInterp
            ).buildValidatorFactory().getValidator();
            return this;
        }

        public ValidationService build() {
            return new ValidationService(parentTeeSeparator, validator, namingStrategy);
        }
    }

}
