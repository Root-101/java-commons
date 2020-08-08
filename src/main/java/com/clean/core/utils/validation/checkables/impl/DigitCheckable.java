package com.clean.core.utils.validation.checkables.impl;

import com.clean.core.utils.validation.checkables.Checkable;

/**
 *
 * @author Jorge
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class DigitCheckable implements Checkable<Character> {

    private final char value;
    private final String source;

    public DigitCheckable(String source, char value) {
        this.source = source;
        this.value = value;
    }

    @Override
    public boolean check() {
        return Character.isDigit(value);
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public Character getValue() {
        return value;
    }

}
