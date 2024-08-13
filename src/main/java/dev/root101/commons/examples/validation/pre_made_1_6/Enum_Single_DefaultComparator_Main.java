package dev.root101.commons.examples.validation.pre_made_1_6;

import dev.root101.commons.validation.ValidationService;
import dev.root101.commons.validation.annotations.EnumValidator;
import dev.root101.commons.validation.annotations.EnumValidatorRegister_String;

public class Enum_Single_DefaultComparator_Main {

    public static void main(String[] args) throws Exception {
        ValidationService validationService = ValidationService.basic();
        
        EnumValidatorRegister_String.setDefaultEnumComparator((currentEnumValue, testValue) -> {
            return true;//never validate
            //return currentEnumValue.toString().equalsIgnoreCase(testValue);
        });

        record Parent(
                @EnumValidator(target = Age_DefComp.class)
                String age) {

        }

        Parent parent = new Parent("other");
        validationService.validateAndThrow(parent);
    }

}

enum Age_DefComp {
    JOUNG("Joung"),
    OLD("Old");

    private final String name;

    private Age_DefComp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

}
