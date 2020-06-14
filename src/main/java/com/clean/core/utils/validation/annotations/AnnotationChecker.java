package com.clean.core.utils.validation.annotations;

import com.clean.core.utils.validation.ValidationResult;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

    private static final HashMap<Class<? extends Annotation>, Class<? extends AnnotationRegister>> register = new HashMap<>();

    static {
        //initial register of ours annotations
        registerAnnotation(GreaterThanCero.class, GreaterThanCeroRegister.class);
        registerAnnotation(LengthExact.class, LengthExactRegister.class);
        registerAnnotation(ListNotEmpty.class, ListNotEmptyRegister.class);
        registerAnnotation(NotNull.class, NotNullRegister.class);
        registerAnnotation(StringNotEmpty.class, StringNotEmptyRegister.class);
        registerAnnotation(RangeDouble.class, RangeDoubleRegister.class);
        registerAnnotation(RangeFloat.class, RangeFloatRegister.class);
        registerAnnotation(RangeInteger.class, RangeIntegerRegister.class);
        registerAnnotation(RangeLong.class, RangeLongRegister.class);
        registerAnnotation(RangeShort.class, RangeShortRegister.class);
        registerAnnotation(Digit.class, DigitRegister.class);
        registerAnnotation(Alphabet.class, AlphabetRegister.class);
    }

    public static void registerAnnotation(Class<? extends Annotation> annotationClass, Class<? extends AnnotationRegister> annotationRegister) {
        register.put(annotationClass, annotationRegister);
    }

    public static void clearAllRegister() {
        register.clear();
    }

    public static void removeRegister(Class<? extends Annotation> annotationClass) {
        register.remove(annotationClass);
    }

    public static boolean containRegister(Class<? extends Annotation> annotationClass) {
        return register.containsKey(annotationClass);
    }

    public static Class<? extends AnnotationRegister> getRegister(Class<? extends Annotation> annotationClass) {
        return register.get(annotationClass);
    }

    public static ValidationResult checkAllFieldsOfObject(Object objectToCheckFor) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        ValidationResult validationResult = new ValidationResult();

        for (Field actualField : objectToCheckFor.getClass().getDeclaredFields()) {
            checkField(objectToCheckFor, actualField, validationResult);
        }

        return validationResult;
    }

    public static void checkField(Object objectToCheckFor, String fieldName, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
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

    private static void checkField(Object objectToCheckFor, Field fieldToCheckFor, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        boolean oldFieldAccessibility = fieldToCheckFor.isAccessible();
        fieldToCheckFor.setAccessible(true);//in case it's private, force the readable

        for (Annotation annotation : fieldToCheckFor.getDeclaredAnnotations()) {
            if (register.containsKey(annotation.annotationType())) {
                Class c = register.get(annotation.annotationType());
                Object newInstance = c.getConstructor().newInstance();
                if (newInstance instanceof AnnotationRegister) {
                    ((AnnotationRegister) newInstance).checkAnnotation(annotation, fieldToCheckFor.get(objectToCheckFor), validationResult);
                } else {
                    throw new ClassCastException("Class " + c + " correspondind to the register of " + annotation.annotationType() + " annotation don't implement the " + AnnotationRegister.class + " interface.");
                }
            }
        }

        fieldToCheckFor.setAccessible(oldFieldAccessibility);
    }
}
