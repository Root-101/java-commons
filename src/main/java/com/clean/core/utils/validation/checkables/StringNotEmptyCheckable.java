/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.clean.core.utils.validation.checkables;

/**
 * 
 * JoBits
 * @author Jorge
 * 
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
