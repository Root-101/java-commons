package com.clean.core.utils.validation.checkables.impl;

import com.clean.core.utils.validation.checkables.Checkable;

/**
 * 
 * @author Jorge
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class DigitCheckable implements Checkable<Character> {

    private final char source;

    public DigitCheckable(char source) {
        this.source = source;
    }

    @Override
    public boolean check() {
        return Character.isDigit(source);
    }

    @Override
    public Character getSource() {
        return source;
    }

}
