package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class RangeLongCheckable implements Checkable<Long> {

    private final long source;
    private final long low;
    private final long hight;

    public RangeLongCheckable(long source, long low, long hight) {
        this.source = source;
        this.low = low;
        this.hight = hight;
    }

    @Override
    public boolean check() {
        return source >= low && source <= hight;
    }

    @Override
    public Long getSource() {
        return source;
    }

}
