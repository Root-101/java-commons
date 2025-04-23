package dev.root101.commons.examples.validation;

import dev.root101.commons.validation.ValidationFieldName;
import dev.root101.commons.validation.ValidationService;
import jakarta.validation.constraints.Size;

class Main_1_4 {

    public static void main(String[] args) throws Exception {
        ValidationService validationService = ValidationService.basic();

        record Parent(
                @ValidationFieldName("parent_name")
                @Size(min = 1, max = 5)
                String parentName) {

        }

        Parent parent = new Parent("Pepito Simple");

        validationService.validate(parent);
    }

}

//ValidationException{statusCode=422 UNPROCESSABLE_ENTITY, messages=[ValidationErrorMessage[source=parentName, invalid_value=Pepito Simple, message=el tama?o debe estar entre 1 y 5]]}
//ValidationException{statusCode=422 UNPROCESSABLE_ENTITY, messages=[ValidationErrorMessage[source=parent_name, invalid_value=Pepito Simple, message=el tama?o debe estar entre 1 y 5]]}
