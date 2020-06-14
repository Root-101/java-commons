package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class NeverCheckable implements Checkable {

    private final Object source;

    public NeverCheckable(Object source) {
        this.source = source;
    }

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public Object getSource() {
        return source;
    }

}
