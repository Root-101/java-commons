package dev.root101.commons.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class EnumMappeableService {

    public static <Y, T extends Enum<?> & EnumMappeable<Y>> List<Y> map(Class<T> clazz) {
        return Stream.of(clazz.getEnumConstants()).map(EnumMappeable::map).toList();
    }

    public static <T extends Enum<?>, Y> List<?> map(Class<T> clazz, Function<T, Y> mapper) {
        return Stream.of(clazz.getEnumConstants()).map(mapper).toList();
    }

}
