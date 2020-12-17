/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.exceptions;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class AlreadyRegisteredService extends RuntimeException {

    public AlreadyRegisteredService(String string) {
        super(string);
    }

}
