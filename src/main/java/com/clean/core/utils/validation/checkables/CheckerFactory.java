package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jorge
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class CheckerFactory {

    public static Checkable buildNotNullCheckable(Object source) {
        return new NotNullCheckable(source);
    }

    public static Checkable buildStringNotEmptyCheckable(String source) {
        return new StringNotEmptyCheckable(source);
    }
}
