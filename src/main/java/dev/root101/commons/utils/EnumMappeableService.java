package dev.root101.commons.utils;

import java.util.List;
import java.util.function.Function;

public class EnumMappeableService {

    public static <Y, T extends Enum & EnumMappeable<Y>> List<Y> map(Class<T> clazz) {
        return List.of(clazz.getEnumConstants()).stream().map((T t) -> {
            return t.map();
        }).toList();
    }

    public static <T extends Enum, Y> List map(Class<T> clazz, Function<T, Y> mapper) {
        return List.of(clazz.getEnumConstants()).stream().map((T t) -> {
            return mapper.apply(t);
        }).toList();
    }

}
