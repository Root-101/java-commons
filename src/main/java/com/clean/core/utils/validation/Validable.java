/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.utils.validation;

import com.clean.core.exceptions.ValidationException;
import java.util.List;

/**
 *
 * @author Jorge
 */
public interface Validable {

    public ValidationResult validate() throws ValidationException;

}
