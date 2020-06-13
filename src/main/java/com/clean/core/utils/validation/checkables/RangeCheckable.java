package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class RangeCheckable<T> implements Checkable<T> {

    private final T source;
    private final T low;
    private final T hight;

    public RangeCheckable(T source, T low, T hight) {
        this.source = source;
        this.low = low;
        this.hight = hight;
    }

    @Override
    public boolean check() {
        if (source instanceof Integer) {
            return checkInteger();
        } else if (source instanceof Float) {
            return checkFloat();
        } else if (source instanceof Long) {
            return checkLong();
        } else if (source instanceof Double) {
            return checkDouble();
        } else if (source instanceof Short) {
            return checkShort();
        }
        return false;
    }

    @Override
    public T getSource() {
        return source;
    }

    private boolean checkInteger() {
        int src = ((Integer) this.source);
        int low = ((Integer) this.low);
        int hight = ((Integer) this.hight);
        return src >= low && src <= hight;
    }

    private boolean checkFloat() {
        float src = ((Float) this.source);
        float low = ((Float) this.low);
        float hight = ((Float) this.hight);
        return src >= low && src <= hight;
    }

    private boolean checkLong() {
        long src = ((Long) this.source);
        long low = ((Long) this.low);
        long hight = ((Long) this.hight);
        return src >= low && src <= hight;
    }

    private boolean checkDouble() {
        double src = ((Double) this.source);
        double low = ((Double) this.low);
        double hight = ((Double) this.hight);
        return src >= low && src <= hight;
    }

    private boolean checkShort() {
        short src = ((Short) this.source);
        short low = ((Short) this.low);
        short hight = ((Short) this.hight);
        return src >= low && src <= hight;
    }

}
