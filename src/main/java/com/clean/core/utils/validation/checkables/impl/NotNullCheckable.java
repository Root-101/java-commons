package com.clean.core.utils.validation.checkables.impl;

import com.clean.core.utils.validation.checkables.Checkable;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class NotNullCheckable implements Checkable {

    private final Object source;

    public NotNullCheckable(Object source) {
        this.source = source;
    }

    @Override
    public boolean check() {
        return source != null;
    }

    @Override
    public Object getSource() {
        return source;
    }

}
