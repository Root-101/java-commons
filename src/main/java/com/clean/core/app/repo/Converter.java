package com.clean.core.app.repo;

import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <Domain>
 * @param <Entity>
 */
public interface Converter<Domain, Entity> {

    public Domain from(Entity object) throws Exception;

    public Entity to(Domain object) throws Exception;

    public List<Domain> from(List<Entity> object) throws Exception;

    public List<Entity> to(List<Domain> object) throws Exception;

}
