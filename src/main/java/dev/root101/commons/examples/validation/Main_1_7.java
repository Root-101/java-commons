package dev.root101.commons.examples.validation;

import dev.root101.commons.validation.ValidationService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

class Main_1_7 {

    public static void main(String[] args) throws Exception {
        ValidationService validationService = ValidationService.basic();

        record SomeValue(
                @NotNull
                List<Integer> accounts,
                @NotNull
                Integer categoryId,
                @Size(max = 1)
                List<SomeValue> more) {

        }

        SomeValue parent = new SomeValue(
                List.of(2),
                4,
                List.of(
                        new SomeValue(List.of(2), 5, List.of())
                )
        );

        validationService.validate(parent);
    }

}

//ValidationException{statusCode=422 UNPROCESSABLE_ENTITY, messages=[ValidationErrorMessage[source=parentName, invalid_value=Pepito Simple, message=el tama?o debe estar entre 1 y 5]]}
//ValidationException{statusCode=422 UNPROCESSABLE_ENTITY, messages=[ValidationErrorMessage[source=parent_name, invalid_value=Pepito Simple, message=el tama?o debe estar entre 1 y 5]]}
