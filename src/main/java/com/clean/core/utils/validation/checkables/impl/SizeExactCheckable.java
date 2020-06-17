package com.clean.core.utils.validation.checkables.impl;

import com.clean.core.utils.validation.checkables.Checkable;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 * @author Jorge
 */
public class SizeExactCheckable implements Checkable<String> {

    private final String source;
    private final int length;

    public SizeExactCheckable(String source, int length) {
        this.source = source;
        this.length = length;
    }

    @Override
    public boolean check() {
        return source.length() == length;
    }

    @Override
    public String getSource() {
        return source;
    }

}
