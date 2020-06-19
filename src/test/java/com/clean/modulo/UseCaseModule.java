package com.clean.modulo;

import com.clean.core.app.usecase.AbstractUseCase;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public interface UseCaseModule extends AbstractModule {

    public <T> T getUseCaseImplementation(Class<? extends AbstractUseCase> classType);
    
    public void registerRepo(RepoModule repo);
}
