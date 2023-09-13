package dev.root101.clean.core.examples.validation.pre_made;

import dev.root101.clean.core.utils.validation.ValidationService;
import dev.root101.clean.core.utils.validation.annotations.EnumValidator;
import dev.root101.clean.core.utils.validation.annotations.EnumValidatorComparator;
import java.util.List;

public class Enum_List_Main {

    public static void main(String[] args) throws Exception {
        record Parent(
                @EnumValidator(target = AgeList.class)
                List<String> ages) {

        }

        Parent parent = new Parent(List.of("some age", "other"));
        ValidationService.validateAndThrow(parent);
    }

}

enum AgeList implements EnumValidatorComparator<String> {
    JOUNG("Joung"),
    OLD("Old");

    private final String name;

    private AgeList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean test(String other) {
        return getName().equalsIgnoreCase(other);
    }

    @Override
    public String toString() {
        return getName();
    }

}
