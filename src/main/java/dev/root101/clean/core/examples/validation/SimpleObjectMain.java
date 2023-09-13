package dev.root101.clean.core.examples.validation;

import dev.root101.clean.core.utils.validation.ValidationService;
import jakarta.validation.constraints.Size;
import lombok.Data;

class SimpleObjectMain {

    public static void main(String[] args) throws Exception {
        PersonalizedNameParent parent = new PersonalizedNameParent("Pepito Simple");

        ValidationService.validateAndThrow(parent);
    }

}

@Data
class SimpleParent {

    @Size(min = 1, max = 5)
    private String parentName;

    public SimpleParent(String name) {
        this.parentName = name;
    }

}
