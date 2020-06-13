package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class RangeFloatCheckable implements Checkable<Float> {

    private final float source;
    private final float low;
    private final float hight;

    public RangeFloatCheckable(float source, float low, float hight) {
        this.source = source;
        this.low = low;
        this.hight = hight;
    }

    @Override
    public boolean check() {
        return source >= low && source <= hight;
    }

    @Override
    public Float getSource() {
        return source;
    }

}
