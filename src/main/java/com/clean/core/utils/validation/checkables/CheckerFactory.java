package com.clean.core.utils.validation.checkables;

import com.clean.core.utils.validation.checkables.impl.DigitCheckable;
import com.clean.core.utils.validation.checkables.impl.NeverCheckable;
import com.clean.core.utils.validation.checkables.impl.SizeExactCheckable;

/**
 *
 * @author Jorge
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class CheckerFactory {

    public static Checkable buildDigitCheckable(String source, Character value) {
        return new DigitCheckable(source, value);
    }

    public static Checkable buildLengthExactCheckable(String source, String value, int length) {
        return new SizeExactCheckable(source, value, length);
    }

    public static Checkable buildNeverCheckable(String source, Object value) {
        return new NeverCheckable(source, value);
    }
}
