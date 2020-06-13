package com.clean.core.utils.validation.annotations;

import com.clean.core.utils.validation.ValidationMessage;
import com.clean.core.utils.validation.ValidationResult;
import com.clean.core.utils.validation.checkables.Checkable;
import com.clean.core.utils.validation.checkables.CheckerFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

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

        Class fieldClass = fieldToCheckFor.getType();
        if (fieldClass.equals(String.class)) {
            checkStringField(objectToCheckFor, fieldToCheckFor, validationResult);
        } else if (fieldClass.equals(int.class) || fieldToCheckFor.getType().equals(Integer.class)) {
            checkIntegerField(objectToCheckFor, fieldToCheckFor, validationResult);
        } else if (fieldClass.equals(float.class) || fieldClass.equals(Float.class)) {
            checkFloatField(objectToCheckFor, fieldToCheckFor, validationResult);
        } else if (fieldClass.equals(long.class) || fieldClass.equals(Long.class)) {
            checkLongField(objectToCheckFor, fieldToCheckFor, validationResult);
        } else if (fieldClass.equals(double.class) || fieldClass.equals(Double.class)) {
            checkDoubleField(objectToCheckFor, fieldToCheckFor, validationResult);
        } else {
            checkObjectField(objectToCheckFor, fieldToCheckFor, validationResult);
        }

        fieldToCheckFor.setAccessible(oldFieldAccessibility);
    }

    private static void checkStringField(Object objectToCheckFor, Field fieldToCheckFor, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException {
        String source = (String) fieldToCheckFor.get(objectToCheckFor);
        for (Annotation annotation : fieldToCheckFor.getDeclaredAnnotations()) {
            if (annotation instanceof StringNotEmpty) {
                StringNotEmpty strNotEmpty = (StringNotEmpty) annotation;

                Checkable ch = CheckerFactory.buildStringNotEmptyCheckable(source);
                ValidationMessage msg = ValidationMessage.from(source, strNotEmpty.message(), strNotEmpty.detailMessage(), strNotEmpty.severity());
                validationResult.check(ch, msg);
            } else if (annotation instanceof LengthExact) {
                LengthExact strLengthExact = (LengthExact) annotation;

                Checkable ch = CheckerFactory.buildLengthExactCheckable(source, strLengthExact.length());
                ValidationMessage msg = ValidationMessage.from(source, strLengthExact.message(), strLengthExact.detailMessage(), strLengthExact.severity());
                validationResult.check(ch, msg);
            }
        }
    }

    private static void checkIntegerField(Object objectToCheckFor, Field fieldToCheckFor, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException {
        int source = (int) fieldToCheckFor.get(objectToCheckFor);
        for (Annotation annotation : fieldToCheckFor.getDeclaredAnnotations()) {
            if (annotation instanceof GreaterThanCeroInteger) {
                GreaterThanCeroInteger grater = (GreaterThanCeroInteger) annotation;

                Checkable ch = CheckerFactory.buildGreaterThanCeroCheckable(source);
                ValidationMessage msg = ValidationMessage.from(source, grater.message(), grater.detailMessage(), grater.severity());
                validationResult.check(ch, msg);
            } else if (annotation instanceof RangeInteger) {
                RangeInteger range = (RangeInteger) annotation;

                Checkable ch = CheckerFactory.buildRangeCheckable(source, range.low(), range.higth());
                ValidationMessage msg = ValidationMessage.from(source, range.message(), range.detailMessage(), range.severity());
                validationResult.check(ch, msg);
            }
        }
    }

    private static void checkLongField(Object objectToCheckFor, Field fieldToCheckFor, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException {
        long source = (long) fieldToCheckFor.get(objectToCheckFor);
        for (Annotation annotation : fieldToCheckFor.getDeclaredAnnotations()) {
            if (annotation instanceof GreaterThanCeroLong) {
                GreaterThanCeroLong grater = (GreaterThanCeroLong) annotation;

                Checkable ch = CheckerFactory.buildGreaterThanCeroCheckable(source);
                ValidationMessage msg = ValidationMessage.from(source, grater.message(), grater.detailMessage(), grater.severity());
                validationResult.check(ch, msg);
            } else if (annotation instanceof RangeLong) {
                RangeLong range = (RangeLong) annotation;

                Checkable ch = CheckerFactory.buildRangeCheckable(source, range.low(), range.higth());
                ValidationMessage msg = ValidationMessage.from(source, range.message(), range.detailMessage(), range.severity());
                validationResult.check(ch, msg);
            }
        }
    }

    private static void checkFloatField(Object objectToCheckFor, Field fieldToCheckFor, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException {
        float source = (float) fieldToCheckFor.get(objectToCheckFor);
        for (Annotation annotation : fieldToCheckFor.getDeclaredAnnotations()) {
            if (annotation instanceof GreaterThanCeroFloat) {
                GreaterThanCeroFloat grater = (GreaterThanCeroFloat) annotation;

                Checkable ch = CheckerFactory.buildGreaterThanCeroCheckable(source);
                ValidationMessage msg = ValidationMessage.from(source, grater.message(), grater.detailMessage(), grater.severity());
                validationResult.check(ch, msg);
            } else if (annotation instanceof RangeFloat) {
                RangeFloat range = (RangeFloat) annotation;

                Checkable ch = CheckerFactory.buildRangeCheckable(source, range.low(), range.higth());
                ValidationMessage msg = ValidationMessage.from(source, range.message(), range.detailMessage(), range.severity());
                validationResult.check(ch, msg);
            }
        }
    }

    private static void checkDoubleField(Object objectToCheckFor, Field fieldToCheckFor, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException {
        double source = (double) fieldToCheckFor.get(objectToCheckFor);
        for (Annotation annotation : fieldToCheckFor.getDeclaredAnnotations()) {
            if (annotation instanceof GreaterThanCeroDouble) {
                GreaterThanCeroDouble grater = (GreaterThanCeroDouble) annotation;

                Checkable ch = CheckerFactory.buildGreaterThanCeroCheckable(source);
                ValidationMessage msg = ValidationMessage.from(source, grater.message(), grater.detailMessage(), grater.severity());
                validationResult.check(ch, msg);
            } else if (annotation instanceof RangeDouble) {
                RangeDouble range = (RangeDouble) annotation;

                Checkable ch = CheckerFactory.buildRangeCheckable(source, range.low(), range.higth());
                ValidationMessage msg = ValidationMessage.from(source, range.message(), range.detailMessage(), range.severity());
                validationResult.check(ch, msg);
            }
        }
    }

    private static void checkObjectField(Object objectToCheckFor, Field fieldToCheckFor, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException {
        for (Annotation annotation : fieldToCheckFor.getDeclaredAnnotations()) {
            if (annotation instanceof NotNull) {
                Object source = fieldToCheckFor.get(objectToCheckFor);

                NotNull notNull = (NotNull) annotation;

                Checkable ch = CheckerFactory.buildNotNullCheckable(source);
                ValidationMessage msg = ValidationMessage.from(source, notNull.message(), notNull.detailMessage(), notNull.severity());
                validationResult.check(ch, msg);
            } else if (annotation instanceof ListNotEmpty) {
                List source = (List) fieldToCheckFor.get(objectToCheckFor);

                ListNotEmpty listNotEmpty = (ListNotEmpty) annotation;

                Checkable ch = CheckerFactory.buildListNotEmptyCheckable(source);
                ValidationMessage msg = ValidationMessage.from(source, listNotEmpty.message(), listNotEmpty.detailMessage(), listNotEmpty.severity());
                validationResult.check(ch, msg);
            }
        }
    }

}
