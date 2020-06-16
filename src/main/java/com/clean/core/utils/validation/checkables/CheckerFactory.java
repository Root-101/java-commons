package com.clean.core.utils.validation.checkables;

import com.clean.core.utils.validation.checkables.impl.GreaterThanCeroCheckable;
import com.clean.core.utils.validation.checkables.impl.DigitCheckable;
import com.clean.core.utils.validation.checkables.impl.LengthExactCheckable;
import com.clean.core.utils.validation.checkables.impl.NeverCheckable;
import com.clean.core.utils.validation.checkables.impl.NotNullCheckable;
import com.clean.core.utils.validation.checkables.impl.AlphabetCheckable;
import com.clean.core.utils.validation.checkables.impl.RangeCheckable;
import com.clean.core.utils.validation.checkables.impl.ListNotEmptyCheckable;
import com.clean.core.utils.validation.checkables.impl.StringNotEmptyCheckable;
import java.util.List;

/**
 *
 * @author Jorge
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class CheckerFactory {

    public static Checkable buildDigitCheckable(Character source) {
        return new DigitCheckable(source);
    }

    public static Checkable buildAlphabetCheckable(Character source) {
        return new AlphabetCheckable(source);
    }

    public static Checkable buildNotNullCheckable(Object source) {
        return new NotNullCheckable(source);
    }

    public static Checkable buildNeverCheckable(Object source) {
        return new NeverCheckable(source);
    }

    public static Checkable buildStringNotEmptyCheckable(String source) {
        return new StringNotEmptyCheckable(source);
    }

    public static Checkable buildLengthExactCheckable(String source, int length) {
        return new LengthExactCheckable(source, length);
    }

    public static Checkable buildListNotEmptyCheckable(List source) {
        return new ListNotEmptyCheckable(source);
    }

    public static Checkable buildGreaterThanCeroCheckable(Object source) {
        return new GreaterThanCeroCheckable(source);
    }

    public static Checkable buildGreaterThanCeroCheckable(int source) {
        return new GreaterThanCeroCheckable<Integer>(source);
    }

    public static Checkable buildGreaterThanCeroCheckable(long source) {
        return new GreaterThanCeroCheckable<Long>(source);
    }

    public static Checkable buildGreaterThanCeroCheckable(float source) {
        return new GreaterThanCeroCheckable<Float>(source);
    }

    public static Checkable buildGreaterThanCeroCheckable(double source) {
        return new GreaterThanCeroCheckable<Double>(source);
    }

    public static Checkable buildRangeCheckable(int source, int low, int hight) {
        return new RangeCheckable<Integer>(source, low, hight);
    }

    public static Checkable buildRangeCheckable(short source, short low, short hight) {
        return new RangeCheckable<Short>(source, low, hight);
    }

    public static Checkable buildRangeCheckable(long source, long low, long hight) {
        return new RangeCheckable<Long>(source, low, hight);
    }

    public static Checkable buildRangeCheckable(float source, float low, float hight) {
        return new RangeCheckable<Float>(source, low, hight);
    }

    public static Checkable buildRangeCheckable(double source, double low, double hight) {
        return new RangeCheckable<Double>(source, low, hight);
    }
}
