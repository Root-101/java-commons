package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class RangeIntegerCheckable implements Checkable<Integer> {

    private final int source;
    private final int low;
    private final int hight;

    public RangeIntegerCheckable(int source, int low, int hight) {
        this.source = source;
        this.low = low;
        this.hight = hight;
    }

    @Override
    public boolean check() {
        return source >= low && source <= hight;
    }

    @Override
    public Integer getSource() {
        return source;
    }

}
