package dev.root101.commons.validation.annotations;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;

public class EnumValidatorRegister_ListOfString implements ConstraintValidator<EnumValidator, List<String>> {

    //general comparator in case EnumValidator don't implement EnumValidatorComparator interface
    private static BiPredicate<? super Enum, String> defaultComparison = (currentEnumValue, testValue) -> {
        return currentEnumValue.toString().equals(testValue);
    };

    //setter for default comparator
    public final static void setDefaultEnumComparator(BiPredicate<? super Enum, String> defaultComparison) {
        Assert.notNull(defaultComparison, "Default comparison can't be null");
        EnumValidatorRegister_ListOfString.defaultComparison = defaultComparison;
    }

    //Enum class
    private Class<? extends Enum<?>> clazz;
    //values of enum
    private Enum[] valuesArr;

    @Override
    public void initialize(EnumValidator _enum) {
        ConstraintValidator.super.initialize(_enum);
        clazz = _enum.target();
        valuesArr = clazz.getEnumConstants();
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        boolean valid = true;//global para si alguno no es valido
        boolean disabledDefault = false;//para solo desabilitar 'context.disableDefaultConstraintViolation();' una vez

        //if enum's targes is assignable from EnumValidatorComparator, compare by the `.test`
        if (EnumValidatorComparator.class.isAssignableFrom(clazz)) {
            for (String singleValue : values) {
                boolean currentExist = Stream.of(valuesArr).anyMatch((t) -> {
                    return ((EnumValidatorComparator<String>) t).test(singleValue);
                });
                if (!currentExist) {
                    valid = false;

                    if (!disabledDefault) {
                        context.disableDefaultConstraintViolation();
                        disabledDefault = false;
                    }

                    context.buildConstraintViolationWithTemplate(
                            String.format(
                                    "'%s' is not one of the one of allowable values: %s".formatted(
                                            singleValue,
                                            Stream.of(valuesArr).map((Object object) -> {
                                                return object.toString();
                                            }).toList().toString()
                                    )
                            )
                    ).addConstraintViolation();
                }
            }
        } else { //if enum's targes is NOT assignable from EnumValidatorComparator, compare by the default
            for (String singleValue : values) {
                boolean currentExist = Stream.of(valuesArr).anyMatch((t) -> {
                    return defaultComparison.test(t, singleValue);
                });
                if (!currentExist) {
                    valid = false;

                    if (!disabledDefault) {
                        context.disableDefaultConstraintViolation();
                        disabledDefault = false;
                    }

                    context.buildConstraintViolationWithTemplate(
                            String.format(
                                    "'%s' is not one of the one of allowable values: %s".formatted(
                                            singleValue,
                                            Stream.of(valuesArr).map((Object object) -> {
                                                return object.toString();
                                            }).toList().toString()
                                    )
                            )
                    ).addConstraintViolation();
                }
            }
        }

        return valid;
    }
}
