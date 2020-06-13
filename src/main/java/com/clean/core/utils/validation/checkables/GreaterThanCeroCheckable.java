package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class GreaterThanCeroCheckable<T> implements Checkable<T> {

    private final T source;

    public GreaterThanCeroCheckable(T source) {
        this.source = source;
    }

    @Override
    public boolean check() {
        if (this.source instanceof Integer) {
            return checkInteger();
        } else if (this.source instanceof Float) {
            return checkFloat();
        } else if (this.source instanceof Long) {
            return checkLong();
        } else if (this.source instanceof Double) {
            return checkDouble();
        }
        return false;
    }

    @Override
    public T getSource() {
        return this.source;
    }

    private boolean checkInteger() {
        return ((Integer) this.source) > 0;
    }

    private boolean checkFloat() {
        return ((Float) this.source) > 0;
    }

    private boolean checkLong() {
        return ((Long) this.source) > 0;
    }

    private boolean checkDouble() {
        return ((Double) this.source) > 0;
    }

}
