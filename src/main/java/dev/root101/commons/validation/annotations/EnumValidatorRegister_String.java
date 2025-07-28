package dev.root101.commons.validation.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;

import java.util.function.BiPredicate;
import java.util.stream.Stream;

public class EnumValidatorRegister_String implements ConstraintValidator<EnumValidator, String> {

    //general comparator in case EnumValidator don't implement EnumValidatorComparator interface
    private static BiPredicate<? super Enum<?>, String> defaultComparison = (currentEnumValue, testValue) -> currentEnumValue.toString().equals(testValue);

    //setter for default comparator
    public static void setDefaultEnumComparator(BiPredicate<? super Enum<?>, String> defaultComparison) {
        Assert.notNull(defaultComparison, "Default comparison can't be null");
        EnumValidatorRegister_String.defaultComparison = defaultComparison;
    }

    //Enum class
    private Class<? extends Enum<?>> clazz;
    //ignore null or not
    private boolean ignoreNull;
    //values of enum
    private Enum<?>[] valuesArr;

    @Override
    public void initialize(EnumValidator _enum) {
        ConstraintValidator.super.initialize(_enum);
        clazz = _enum.target();
        ignoreNull = _enum.ignoreNull();
        valuesArr = clazz.getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null && ignoreNull) {
            return true;
        }
        boolean present;

        //if enum's target is assignable from EnumValidatorComparator, compare by the `.test`
        if (EnumValidatorComparator.class.isAssignableFrom(clazz)) {
            present = Stream.of(valuesArr).anyMatch(
                    (t) -> ((EnumValidatorComparator<String>) t).test(value)
            );
        } else {
            //if enum's targes is NOT assignable from EnumValidatorComparator, compare by the default
            present = Stream.of(valuesArr).anyMatch(
                    (t) -> defaultComparison.test(t, value)
            );
        }

        //if the value is NOT present, show custom error
        if (!present) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    String.format(
                            "'%s' is not one of the allowable values: %s".formatted(
                                    value,
                                    Stream.of(valuesArr).map(
                                            (Object object) -> object.toString()
                                    ).toList().toString()
                            )
                    )
            ).addConstraintViolation();
        }

        return present;
    }
}
