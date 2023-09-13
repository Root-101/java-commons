package dev.root101.clean.core.examples.validation.pre_made;

import dev.root101.clean.core.utils.validation.ValidationService;
import dev.root101.clean.core.utils.validation.annotations.Digit;

public class Digit_Main {

    public static void main(String[] args) throws Exception {
        record Parent(
                @Digit()
                Character sex) {

        }

        Parent parent = new Parent('0');
        ValidationService.validateAndThrow(parent);
    }

}
