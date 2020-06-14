package com.clean.core.utils.validation.checkables;

import java.util.List;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 * @author Jorge
 * @param <T>
 */
public class ListNotEmptyCheckable<T> implements Checkable<List<T>> {

    private final List<T> source;

    public ListNotEmptyCheckable(List<T> source) {
        this.source = source;
    }

    @Override
    public boolean check() {
        return source != null && !source.isEmpty();
    }

    @Override
    public List<T> getSource() {
        return source;
    }

}
