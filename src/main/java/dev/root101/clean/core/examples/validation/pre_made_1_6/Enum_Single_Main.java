package dev.root101.clean.core.examples.validation.pre_made_1_6;

import dev.root101.clean.core.utils.validation.ValidationService;
import dev.root101.clean.core.utils.validation.annotations.EnumValidator;
import dev.root101.clean.core.utils.validation.annotations.EnumValidatorComparator;

public class Enum_Single_Main {

    public static void main(String[] args) throws Exception {
        record Parent(
                @EnumValidator(target = Age.class)
                String age) {

        }

        Parent parent = new Parent("other");
        ValidationService.validateAndThrow(parent);
    }

}

enum Age implements EnumValidatorComparator<String> {
    JOUNG("Joung"),
    OLD("Old");

    private final String name;

    private Age(String name) {
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
