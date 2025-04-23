package dev.root101.commons.examples.validation.pre_made_1_6;

import dev.root101.commons.validation.ValidationService;
import dev.root101.commons.validation.annotations.EnumValidator;
import dev.root101.commons.validation.annotations.EnumValidatorComparator;
import java.util.List;

public class Enum_List_Main {

    public static void main(String[] args) throws Exception {
        ValidationService validationService = ValidationService.basic();
        
        record Parent(
                @EnumValidator(target = AgeList.class)
                List<String> ages) {

        }

        Parent parent = new Parent(List.of("some age", "other"));
        validationService.validate(parent);
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
