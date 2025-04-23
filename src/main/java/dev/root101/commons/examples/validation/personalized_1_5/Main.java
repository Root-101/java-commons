package dev.root101.commons.examples.validation.personalized_1_5;

import dev.root101.commons.validation.ValidationService;

public class Main {

    public static void main(String[] args) throws Exception {
        ValidationService validationService = ValidationService.basic();

        record Parent(
                @PersonalizedValidation(name = "Pepito")
                String parentName) {

        }
        Parent parent = new Parent("Pepito Simple");

        validationService.validate(parent);
    }

}
