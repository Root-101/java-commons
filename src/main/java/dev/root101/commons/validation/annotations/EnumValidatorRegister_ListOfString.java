package dev.root101.commons.validation.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public class EnumValidatorRegister_ListOfString implements ConstraintValidator<EnumValidator, List<String>> {

    //general comparator in case EnumValidator don't implement EnumValidatorComparator interface
    private static BiPredicate<? super Enum<?>, String> defaultComparison = (currentEnumValue, testValue) -> currentEnumValue.toString().equals(testValue);

    //setter for default comparator
    public static void setDefaultEnumComparator(BiPredicate<? super Enum<?>, String> defaultComparison) {
        Assert.notNull(defaultComparison, "Default comparison can't be null");
        EnumValidatorRegister_ListOfString.defaultComparison = defaultComparison;
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
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        boolean valid = true;//global para si alguno no es valido
        boolean disabledDefault = false;//para solo desabilitar 'context.disableDefaultConstraintViolation();' una vez

        //if enum's targes is assignable from EnumValidatorComparator, compare by the `.test`
        if (EnumValidatorComparator.class.isAssignableFrom(clazz)) {
            for (String singleValue : values) {
                if (singleValue == null && ignoreNull) {
                    continue;
                }
                boolean currentExist = Stream.of(valuesArr).anyMatch(
                        (t) -> ((EnumValidatorComparator<String>) t).test(singleValue)
                );
                if (!currentExist) {
                    valid = false;

                    if (!disabledDefault) {
                        context.disableDefaultConstraintViolation();
                        disabledDefault = true;
                    }

                    context.buildConstraintViolationWithTemplate(
                            String.format(
                                    "'%s' is not one of the allowable values: %s".formatted(
                                            singleValue,
                                            Stream.of(valuesArr).map(
                                                    (Object object) -> object.toString()
                                            ).toList().toString()
                                    )
                            )
                    ).addConstraintViolation();
                }
            }
        } else { //if enum's targes is NOT assignable from EnumValidatorComparator, compare by the default
            for (String singleValue : values) {
                if (singleValue == null && ignoreNull) {
                    continue;
                }
                boolean currentExist = Stream.of(valuesArr).anyMatch(
                        (t) -> defaultComparison.test(t, singleValue)
                );
                if (!currentExist) {
                    valid = false;

                    if (!disabledDefault) {
                        context.disableDefaultConstraintViolation();
                        disabledDefault = true;
                    }

                    context.buildConstraintViolationWithTemplate(
                            String.format(
                                    "'%s' is not one of the allowable values: %s".formatted(
                                            singleValue,
                                            Stream.of(valuesArr).map(
                                                    (Object object) -> object.toString()
                                            ).toList().toString()
                                    )
                            )
                    ).addConstraintViolation();
                }
            }
        }

        return valid;
    }
}
