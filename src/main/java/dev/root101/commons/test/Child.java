/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.root101.clean.core.test;

import dev.root101.clean.core.utils.validation.ValidationFieldName;
import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Yo
 */
public class Child {

    @NotNull
    @Size(max = 5)
    @ValidationFieldName("child_name")
    private String childName;

    @NotNull
    @NotEmpty
    @ValidationFieldName("toys")
    private List<Toy> toys;

    public Child(String childName, List<Toy> toys) {
        this.childName = childName;
        this.toys = toys;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public List<Toy> getToys() {
        return toys;
    }

    public void setToys(List<Toy> toys) {
        this.toys = toys;
    }

}
