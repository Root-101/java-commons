package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class GreaterThanCeroLongCheckable implements Checkable<Long> {

    private final long source;

    public GreaterThanCeroLongCheckable(long source) {
        this.source = source;
    }

    @Override
    public boolean check() {
        return source > 0;
    }

    @Override
    public Long getSource() {
        return source;
    }

}
