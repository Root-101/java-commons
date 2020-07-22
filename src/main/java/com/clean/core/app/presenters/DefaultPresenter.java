/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.app.presenters;

import com.clean.core.app.services.Navigation;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public abstract class DefaultPresenter implements AbstractPresenter {

    @Override
    public void navigateTo(String to, Object... caller) {
        Navigation.navigateTo(to, caller);
    }

}
