package com.clean.core.utils.validation.annotations;

import com.clean.core.utils.validation.ValidationResult;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Notas: Para el field.get(object):<br/>
 * - La clase Field tiene métodos específicos para los get de los diferentes
 * tipos de datos soportados, pero si internamente hace la conversión con el
 * inboxing-outboxing lanza excepción. Por lo tanto se decidió hacer el casteo
 * manual.<br/>
 * Ejemplo:<br/>
 * fieldToCheckFor.getType() = (java.lang.Class) class java.lang.Integer, y int
 * source = fieldToCheckFor.getInt(objectToCheckFor);<br/>
 * Lanza excepción: java.lang.IllegalArgumentException: Attempt to get
 * java.lang.Integer field "field.name.abc" with illegal data type conversion to
 * int.<br/>
 * Para resolver ese probleme se usa:<br/>
 * int source = (int) fieldToCheckFor.get(objectToCheckFor);
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class AnnotationChecker {

    private static final HashMap<Class, AnnotationRegister> register = new HashMap<>();

    static {
        //initial register of ours annotations
        registerAnnotation(GreaterThanCero.class, new GreaterThanCeroRegister());
        registerAnnotation(LengthExact.class, new LengthExactRegister());
        registerAnnotation(ListNotEmpty.class, new ListNotEmptyRegister());
        registerAnnotation(NotNull.class, new NotNullRegister());
        registerAnnotation(StringNotEmpty.class, new StringNotEmptyRegister());
    }

    public static void registerAnnotation(Class annotationClass, AnnotationRegister annotationRegister) {
        register.put(annotationClass, annotationRegister);
    }

    public static ValidationResult checkAllFieldsOfObject(Object objectToCheckFor) throws IllegalArgumentException, IllegalAccessException {
        ValidationResult validationResult = new ValidationResult();

        for (Field actualField : objectToCheckFor.getClass().getDeclaredFields()) {
            checkField(objectToCheckFor, actualField, validationResult);
        }

        return validationResult;
    }

    public static void checkField(Object objectToCheckFor, String fieldName, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException {
        boolean foundField = false;
        for (Field actualField : objectToCheckFor.getClass().getDeclaredFields()) {
            if (actualField.getName().equals(fieldName)) {
                checkField(objectToCheckFor, actualField, validationResult);
                foundField = true;
            }
        }
        if (!foundField) {
            throw new NullPointerException("The object don't contain the field: " + fieldName);
        }
    }

    private static void checkField(Object objectToCheckFor, Field fieldToCheckFor, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException {
        boolean oldFieldAccessibility = fieldToCheckFor.isAccessible();
        fieldToCheckFor.setAccessible(true);//in case it's private, force the readable

        for (Annotation annotation : fieldToCheckFor.getDeclaredAnnotations()) {
            if (register.containsKey(annotation.annotationType())) {
                register.get(annotation.annotationType()).checkAnnotation(annotation, fieldToCheckFor.get(objectToCheckFor), validationResult);
            }
        }

        fieldToCheckFor.setAccessible(oldFieldAccessibility);
    }
}
