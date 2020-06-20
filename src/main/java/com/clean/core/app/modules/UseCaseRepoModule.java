package com.clean.core.app.modules;

import com.clean.core.app.usecase.AbstractUseCase;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public interface UseCaseRepoModule extends AbstractModule {

    public <T> T getUseCaseImplementation(Class<? extends AbstractUseCase> classType);
    
    public void registerRepo(RepoModule repo);
}
