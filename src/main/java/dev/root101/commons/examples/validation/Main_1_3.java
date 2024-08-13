package dev.root101.commons.examples.validation;

import dev.root101.commons.validation.ValidationService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

class Main_1_3 {

    public static void main(String[] args) throws Exception {
        ValidationService validationService = ValidationService.basic();
        
        record Child(
                @Size(min = 1, max = 5)
                String childName) {

        }
        record Parent(
                @Size(min = 1, max = 5)
                String parentName,
                @NotEmpty
                List<Child> childrens) {

        }

        Parent parent = new Parent(
                "Pepito",
                List.of(
                        new Child(
                                "Pepito Junior"
                        ),
                        new Child(
                                "Pepito Junior 2"
                        )
                )
        );

        validationService.validateRecursiveAndThrow(parent);
        //or
        /*ValidationService.validateAndThrow(
                parent,
                parent.childs().get(0),
                parent.childs().get(1)
        );*/
    }

}
