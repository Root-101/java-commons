package dev.root101.clean.core.test;

import dev.root101.clean.core.utils.validation.ValidationService;
import java.util.List;

class Main {

    public static void main(String[] args) throws Exception {
        Parent parent = new Parent("name111", ParentType.ACTIVE, List.of(new Toy("toy name 1")));
        ValidationService.validateRecursiveAndThrow(parent);
    }

}
