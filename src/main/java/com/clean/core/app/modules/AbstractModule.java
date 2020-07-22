package com.clean.core.app.modules;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public interface AbstractModule {

    public String getModuleName();

    public <T> T getImplementation(Class<T> classType);

    public <T> T getImplementation(String moduleName, Class<T> classType);

    public void registerModule(AbstractModule classType);

    public void removeModule(AbstractModule classType);

    public void removeModule(String moduleName);

}
