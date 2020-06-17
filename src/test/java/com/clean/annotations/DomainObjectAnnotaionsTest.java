package com.clean.annotations;

import com.clean.*;
import com.clean.core.utils.validation.Validable;
import com.clean.core.utils.validation.ValidationResult;
import com.clean.core.utils.validation.annotations.Digit;
import com.clean.core.utils.validation.annotations.SizeExact;
import javax.validation.ValidationException;
import javax.validation.constraints.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * JoBits
 *
 * @author Jorge
 *
 */
public class DomainObjectAnnotaionsTest implements Validable {

    private int cant = 8;

    @NotNull(message = "Sting no vacio Annotation")
    private String hola = "123";

    @Digit(message = "esto no es un digito")
    private char c = '1';

    @SizeExact(length = 5, message = "Esto no tiene 5 digitos")
    private String abc = "1234";

    public DomainObjectAnnotaionsTest() {
        validate();
    }

    @Override
    public ValidationResult validate() throws ValidationException {
        ValidationResult ret = new ValidationResult();
        //ret.check(CheckerFactory.buildNotNullCheckable(hola), "String no vacio");
        ret.checkFromAnnotations(this);
        //ret.checkFromAnnotations(this, "hola");

        return ret.throwException();
    }

}
