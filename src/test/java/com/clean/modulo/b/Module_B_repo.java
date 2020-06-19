package com.clean.modulo.b;

import com.clean.modulo.a.CRUDRepo;
import com.clean.core.app.repo.AbstractRepository;
import com.clean.modulo.RepoModule;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class Module_B_repo implements RepoModule {

    @Override
    public <T> T getUseCaseImplementation(Class<? extends AbstractRepository> classType) {
        if (classType == CRUDRepo.class) {
            return new CRUDRepoImpl();
        }
        return null;
    }

    @Override
    public String getModuleName() {
        return "modulo b repo";
    }

}
