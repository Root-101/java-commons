package dev.root101.commons.examples.validation;

import dev.root101.commons.validation.ValidationService;
import jakarta.validation.constraints.Size;

class Main_1_2 {

    public static void main(String[] args) throws Exception {
        record Parent(
                @Size(min = 1, max = 5)
                String parentName) {

        }
        Parent parent = new Parent("Pepito Simple");

        ValidationService.validateAndThrow(parent);
    }

}
