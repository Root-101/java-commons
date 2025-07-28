package dev.root101.commons.validation.annotations;

import java.util.List;
import java.util.stream.Stream;

/**
 * Estado del envelope, indica si se firmo bien, si se cancelo o lo que sea
 *
 * @author JesusHdezWaterloo@Github
 */
public enum SortType implements EnumValidatorComparator<String> {
    ASC("ASC"),
    DESC("DESC"),
    NONE("0");

    private final String name;

    private SortType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean test(String statusName) {
        return this.toString().equalsIgnoreCase(statusName);
    }

    public static boolean contain(String type) {
        return Stream.of(
                SortType.values()
        ).anyMatch((SortType t) -> t.test(type));
    }

    public static List<String> all() {
        return Stream.of(SortType.values()).map(
                SortType::toString
        ).toList();
    }
}
