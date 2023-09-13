package dev.root101.clean.core.examples.validation.personalized;

import lombok.Data;

@Data
public class Parent {

    @PersonalizedValidation(name = "Pepito")
    private String parentName;

    public Parent(String parentName) {
        this.parentName = parentName;
    }

}
