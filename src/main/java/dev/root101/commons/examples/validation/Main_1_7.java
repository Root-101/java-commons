package dev.root101.commons.examples.validation;

import dev.root101.commons.validation.ValidationService;
import jakarta.validation.constraints.NotNull;

import java.util.List;

class Main_1_7 {

    public static void main(String[] args) throws Exception {
        ValidationService validationService = ValidationService.basic();

        record SomeValue(
                @NotNull
                List<Integer> accounts,
                @NotNull
                Integer categoryId) {

        }

        SomeValue parent = new SomeValue(List.of(2), 4);

        validationService.validateRecursiveAndThrow(parent);

        System.out.println("All oka");
    }

}

//ValidationException{statusCode=422 UNPROCESSABLE_ENTITY, messages=[ValidationErrorMessage[source=parentName, invalid_value=Pepito Simple, message=el tama?o debe estar entre 1 y 5]]}
//ValidationException{statusCode=422 UNPROCESSABLE_ENTITY, messages=[ValidationErrorMessage[source=parent_name, invalid_value=Pepito Simple, message=el tama?o debe estar entre 1 y 5]]}
