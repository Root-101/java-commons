package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class GreaterThanCeroDoubleCheckable implements Checkable<Double> {

    private final double source;

    public GreaterThanCeroDoubleCheckable(double source) {
        this.source = source;
    }

    @Override
    public boolean check() {
        return source > 0;
    }

    @Override
    public Double getSource() {
        return source;
    }

}
