package dev.root101.commons.examples.validation.pre_made_1_6;

import dev.root101.commons.validation.ValidationService;
import dev.root101.commons.validation.annotations.Digit;

public class Digit_Main {

    public static void main(String[] args) throws Exception {
        ValidationService validationService = ValidationService.basic();
        
        record Parent(
                @Digit()
                Character sex) {

        }

        Parent parent = new Parent('a');
        validationService.validate(parent);
    }

}
