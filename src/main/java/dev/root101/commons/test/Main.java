package dev.root101.commons.test;

import dev.root101.commons.validation.ValidationService;
import java.util.List;

class Main {

    public static void main(String[] args) throws Exception {
        ValidationService validationService = ValidationService.builder().build();

        Parent parent = new Parent("name111", ParentType.ACTIVE, List.of(new Toy("toy name 1")));

        validationService.validate(parent);
    }

}
