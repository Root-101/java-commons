package com.clean.core.app.repo;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <Domain>
 * @param <Entity>
 */
public interface Converter<Domain, Entity> {

    public Domain from(Entity object);

    public Entity to(Domain object);

}
