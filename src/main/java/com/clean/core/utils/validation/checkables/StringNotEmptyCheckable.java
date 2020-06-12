package com.clean.core.utils.validation.checkables;

/**
 * 
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 * @author Jorge
 */
public class StringNotEmptyCheckable implements Checkable<String>{
    
    private String source;

    public StringNotEmptyCheckable(String source) {
        this.source = source;
    }

    @Override
    public boolean check() {
        return !source.isEmpty();
    }

    @Override
    public String getSource() {
        return source;
    }

}
