package com.clean.core.app.repo;

import java.util.ArrayList;
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

    public default List<Domain> from(List<Entity> list) throws Exception {
        List<Domain> answ = new ArrayList<>();
        for (Entity entity : list) {
            answ.add(from(entity));
        }
        return answ;
    }

    public default List<Entity> to(List<Domain> list) throws Exception {//convert entities to domain
        List<Entity> answ = new ArrayList<>();
        for (Domain domain : list) {
            answ.add(to(domain));
        }
        return answ;
    }

}
