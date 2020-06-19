package com.clean.modulo.a;

import com.clean.core.app.usecase.AbstractUseCase;
import com.clean.modulo.UseCaseModule;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class Module_A_USECASE implements UseCaseModule {

    @Override
    public <T> T getUseCaseImplementation(Class<? extends AbstractUseCase> classType) {
        return null;
    }

    @Override
    public String getModuleName() {
        return "module A";
    }

}
