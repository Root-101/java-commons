package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class RangeDoubleCheckable implements Checkable<Double> {

    private final double source;
    private final double low;
    private final double hight;

    public RangeDoubleCheckable(double source, double low, double hight) {
        this.source = source;
        this.low = low;
        this.hight = hight;
    }

    @Override
    public boolean check() {
        return source >= low && source <= hight;
    }

    @Override
    public Double getSource() {
        return source;
    }

}
