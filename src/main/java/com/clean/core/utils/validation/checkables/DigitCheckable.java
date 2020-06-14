package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 * @author Jorge
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
