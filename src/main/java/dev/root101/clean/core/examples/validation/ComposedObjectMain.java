package dev.root101.clean.core.examples.validation;

import dev.root101.clean.core.utils.validation.ValidationService;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

class ComposedObjectMain {

    public static void main(String[] args) throws Exception {
        ComposedParent parent = new ComposedParent(
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

        //ValidationService.validateRecursiveAndThrow(parent);
        //or
        ValidationService.validateAndThrow(
                parent,
                parent.getChilds().get(0),
                parent.getChilds().get(1)
        );
    }

}

@Data
class ComposedParent {

    @Size(min = 1, max = 5)
    private String parentName;

    private List<Child> childs;

    public ComposedParent(String name, List<Child> childs) {
        this.parentName = name;
        this.childs = childs;
    }

}

@Data
class Child {

    @Size(min = 1, max = 5)
    private String childName;

    public Child(String name) {
        this.childName = name;
    }

}
