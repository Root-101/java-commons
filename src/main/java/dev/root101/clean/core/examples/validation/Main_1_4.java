package dev.root101.clean.core.examples.validation;

import dev.root101.clean.core.utils.validation.ValidationFieldName;
import dev.root101.clean.core.utils.validation.ValidationService;
import jakarta.validation.constraints.Size;

class Main_1_4 {

    public static void main(String[] args) throws Exception {
        record Parent(
                @ValidationFieldName("parent_name")
                @Size(min = 1, max = 5)
                String parentName) {

        }

        Parent parent = new Parent("Pepito Simple");

        ValidationService.validateAndThrow(parent);
    }

}

//ValidationException{statusCode=422 UNPROCESSABLE_ENTITY, messages=[ValidationErrorMessage[source=parentName, invalid_value=Pepito Simple, message=el tama?o debe estar entre 1 y 5]]}
//ValidationException{statusCode=422 UNPROCESSABLE_ENTITY, messages=[ValidationErrorMessage[source=parent_name, invalid_value=Pepito Simple, message=el tama?o debe estar entre 1 y 5]]}
