/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.root101.clean.core.utils.validation.annotations;

import dev.root101.clean.core.utils.validation.annotations.EnumValue;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.BiPredicate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 *
 * @author JesusHdezWaterloo@Github
 */
@Slf4j
public class EnumValueRegister_String implements ConstraintValidator<EnumValue, String> {

    private static BiPredicate<? super Enum, String> defaultComparison = (currentEnumValue, testValue) -> {
        return currentEnumValue.name().equals(testValue);
    };

    public static void setDefaultEnumComparator(BiPredicate<? super Enum, String> defaultComparison) {
        Assert.notNull(defaultComparison, "Default comparison can't be null");
        EnumValueRegister_String.defaultComparison = defaultComparison;
    }

    private Class clazz;

    @Override
    public void initialize(EnumValue a) {
        ConstraintValidator.super.initialize(a);
        clazz = a.target();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //saco los valores del enum para usarlos despues
        Enum[] valuesArr;
        try {
            Method valuesMethod = clazz.getDeclaredMethod("values");
            valuesArr = (Enum[]) valuesMethod.invoke(null);
        } catch (Exception e) {//nunca debe entrar aqui
            valuesArr = new Enum[0];
        }

        try {
            boolean present;
            try {
                //trato de validar el objeto por el metodo estatico 'isPresent'
                Method containMethod = clazz.getDeclaredMethod("isPresent", String.class);
                present = (boolean) containMethod.invoke(null, value);
            } catch (NoSuchMethodException ex) {
                log.error("No static 'isPresent(java.lang.String)' method in: " + clazz.getCanonicalName() + ". Using default comparison of enum in '" + EnumValueRegister_String.class.getCanonicalName() + "'.");

                //Si dio el error de 'NoSuchMethodException', que no tiene ese metodo implementado, me voy a la comparacion por defecto
                present = List.of(valuesArr).stream().anyMatch((currentEnum) -> {
                    return defaultComparison.test(currentEnum, value);
                });
            }

            if (!present) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(String
                        .format(
                                "'%s' is not one of the one of allowable values: %s".formatted(value,
                                        List.of(valuesArr).stream().map((Object object) -> {
                                            return object.toString();
                                        }).toList().toString())
                        ))
                        .addConstraintViolation();
            }

            return present;
        } catch (Exception ex) {
            log.error(ex.toString());
            return false;
        }
    }
}
