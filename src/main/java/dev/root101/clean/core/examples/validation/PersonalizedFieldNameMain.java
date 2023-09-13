package dev.root101.clean.core.examples.validation;

import dev.root101.clean.core.utils.validation.ValidationFieldName;
import dev.root101.clean.core.utils.validation.ValidationService;
import jakarta.validation.constraints.Size;
import lombok.Data;

class PersonalizedFieldNameMain {

    public static void main(String[] args) throws Exception {
        PersonalizedNameParent parent = new PersonalizedNameParent("Pepito Simple");

        ValidationService.validateAndThrow(parent);
    }

}

@Data
class PersonalizedNameParent {

    @ValidationFieldName("parent_name")
    @Size(min = 1, max = 5)
    private String parentName;

    public PersonalizedNameParent(String parentName) {
        this.parentName = parentName;
    }

}
//ValidationException{statusCode=422 UNPROCESSABLE_ENTITY, messages=[ValidationErrorMessage[source=parentName, invalid_value=Pepito Simple, message=el tama?o debe estar entre 1 y 5]]}
//ValidationException{statusCode=422 UNPROCESSABLE_ENTITY, messages=[ValidationErrorMessage[source=parent_name, invalid_value=Pepito Simple, message=el tama?o debe estar entre 1 y 5]]}
