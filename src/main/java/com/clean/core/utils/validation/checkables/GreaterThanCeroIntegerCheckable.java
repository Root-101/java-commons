package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class GreaterThanCeroIntegerCheckable implements Checkable<Integer> {

    private final int source;

    public GreaterThanCeroIntegerCheckable(int source) {
        this.source = source;
    }

    @Override
    public boolean check() {
        return source > 0;
    }

    @Override
    public Integer getSource() {
        return source;
    }

}
