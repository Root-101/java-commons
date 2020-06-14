package com.clean;


import com.clean.core.utils.validation.Validable;
import com.clean.core.utils.validation.ValidationResult;
import com.clean.core.utils.validation.checkables.CheckerFactory;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * JoBits
 * @author Jorge
 * 
 */
public class DomainObjectExample  implements Validable{

    private int cant = 8;
    
    @NotNull(message = "Sting no vacio Annotation")
    private String hola = null;
    
    @Override
    public ValidationResult validate() throws ValidationException {
        ValidationResult ret = new ValidationResult();
        ret.check(CheckerFactory.buildNotNullCheckable(hola), "String no vacio");
        ret.check(this);
        return ret.throwException();
    }
    
    public static void main(String[] args) {
        new DomainObjectExample().validate();
    }

}
