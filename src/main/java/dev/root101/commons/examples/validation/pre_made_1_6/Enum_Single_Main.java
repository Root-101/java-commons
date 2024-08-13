package dev.root101.commons.examples.validation.pre_made_1_6;

import dev.root101.commons.validation.ValidationService;
import dev.root101.commons.validation.annotations.EnumValidator;
import dev.root101.commons.validation.annotations.EnumValidatorComparator;

public class Enum_Single_Main {

    public static void main(String[] args) throws Exception {
        ValidationService validationService = ValidationService.basic();
        
        record Parent(
                @EnumValidator(target = Age.class)
                String age) {

        }

        Parent parent = new Parent("other");
        validationService.validateAndThrow(parent);
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
