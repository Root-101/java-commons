package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 * @author Jorge
 */
public class AlphabetCheckable implements Checkable<Character> {

    private final char source;

    public AlphabetCheckable(char source) {
        this.source = source;
    }

    @Override
    public boolean check() {
        return Character.isAlphabetic(source);
    }

    @Override
    public Character getSource() {
        return source;
    }

}
