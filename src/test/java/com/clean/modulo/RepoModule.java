package com.clean.modulo;

import com.clean.core.app.repo.AbstractRepository;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public interface RepoModule extends AbstractModule {

    public <T> T getUseCaseImplementation(Class<? extends AbstractRepository> classType);

}
