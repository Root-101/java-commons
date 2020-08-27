package com.clean.core.utils;

import com.clean.core.utils.SortBy.List;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
@Retention(RUNTIME)
@Target(TYPE)
@Repeatable(List.class)
public @interface SortBy {

    public final static int ASCENDING = 1;
    public final static int DESCENDING = -1;

    /**
     * Sort the objects by it's fields acording to this priority, basically sort
     * by the first, if 2 or more are equals, sort it by the 2nd and so on.
     *
     * @return String array with fields name by decreasing priority
     */
    String[] priority();

    int order() default ASCENDING;

    /**
     * Defines several {@link SortBy} constraints on the same element.
     *
     * @see SortBy
     */
    @Target(TYPE)
    @Retention(RUNTIME)
    @interface List {

        SortBy[] value();
    }
}
