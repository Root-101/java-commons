package com.clean.core.utils.validation.checkables.impl;

import com.clean.core.utils.validation.checkables.Checkable;

/**
 *
 * @author Jorge
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class NeverCheckable implements Checkable {

    private final String source;
    private final Object value;

    public NeverCheckable(String source, Object value) {
        this.source = source;
        this.value = value;
    }

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public Object getValue() {
        return value;
    }

}
