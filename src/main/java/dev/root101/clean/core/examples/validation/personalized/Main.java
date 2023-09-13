package dev.root101.clean.core.examples.validation.personalized;

import dev.root101.clean.core.utils.validation.ValidationService;

public class Main {

    public static void main(String[] args) throws Exception {
        Parent parent = new Parent("Pepito Simple");

        ValidationService.validateAndThrow(parent);
    }

}
