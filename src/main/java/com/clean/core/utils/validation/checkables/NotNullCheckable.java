/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.utils.validation.checkables;

import com.clean.core.utils.validation.checkables.Checkable;

/**
 *
 * JoBits
 *
 * @author Jorge
 *
 */
public class NotNullCheckable implements Checkable {

    private Object source;

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
