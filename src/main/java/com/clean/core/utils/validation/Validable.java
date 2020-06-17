package com.clean.core.utils.validation;

import javax.validation.ValidationException;

/**
 * 
 * @author Jorge
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public interface Validable {

    public ValidationResult validate() throws ValidationException;

}
