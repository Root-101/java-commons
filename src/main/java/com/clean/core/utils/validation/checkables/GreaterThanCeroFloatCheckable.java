package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class GreaterThanCeroFloatCheckable implements Checkable<Float> {

    private final float source;

    public GreaterThanCeroFloatCheckable(float source) {
        this.source = source;
    }

    @Override
    public boolean check() {
        return source > 0;
    }

    @Override
    public Float getSource() {
        return source;
    }

}
