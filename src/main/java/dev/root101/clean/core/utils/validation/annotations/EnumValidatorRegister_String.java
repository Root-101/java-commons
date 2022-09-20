package dev.root101.clean.core.utils.validation.annotations;

import java.util.function.BiPredicate;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

@Slf4j
public class EnumValidatorRegister_String implements ConstraintValidator<EnumValidator, String> {

    //general comparator in case EnumValidator don't implement EnumValidatorComparator interface
    private static BiPredicate<? super Enum, String> defaultComparison = (currentEnumValue, testValue) -> {
        return currentEnumValue.toString().equals(testValue);
    };

    //setter for default comparator
    public static void setDefaultEnumComparator(BiPredicate<? super Enum, String> defaultComparison) {
        Assert.notNull(defaultComparison, "Default comparison can't be null");
        EnumValidatorRegister_String.defaultComparison = defaultComparison;
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
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean present;

        //if enum's targes is assignable from EnumValidatorComparator, compare by the `.test`
        if (EnumValidatorComparator.class.isAssignableFrom(clazz)) {
            present = Stream.of(valuesArr).anyMatch((t) -> {
                return ((EnumValidatorComparator<String>) t).test(value);
            });
        } else { //if enum's targes is NOT assignable from EnumValidatorComparator, compare by the default
            present = Stream.of(valuesArr).anyMatch((t) -> {
                return defaultComparison.test(t, value);
            });
        }

        //if the value is NOT present, show custom error
        if (!present) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    String.format(
                            "'%s' is not one of the one of allowable values: %s".formatted(
                                    value,
                                    Stream.of(valuesArr).map((Object object) -> {
                                        return object.toString();
                                    }).toList().toString()
                            )
                    )
            ).addConstraintViolation();
        }

        return present;
    }
}
