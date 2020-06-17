package com.clean.core.utils.validation.checkables;

import com.clean.core.utils.validation.checkables.impl.DigitCheckable;
import com.clean.core.utils.validation.checkables.impl.NeverCheckable;
import com.clean.core.utils.validation.checkables.impl.SizeExactCheckable;

/**
 *
 * @author Jorge
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class CheckerFactory {

    public static Checkable buildDigitCheckable(Character source) {
        return new DigitCheckable(source);
    }

    public static Checkable buildLengthExactCheckable(String source, int length) {
        return new SizeExactCheckable(source, length);
    }

    public static Checkable buildNeverCheckable(Object source) {
        return new NeverCheckable(source);
    }
}
