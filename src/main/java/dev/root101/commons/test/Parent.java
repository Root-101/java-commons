/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.root101.clean.core.test;

import dev.root101.clean.core.utils.validation.ValidationFieldName;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Yo
 */
public class Parent {

    @Size(max = 5)
    @ValidationFieldName("parent_name")
    private String parentName;

    @ValidationFieldName("type")
    @NotNull
    private ParentType type;

    @ValidationFieldName("parent_toys")
    private List<Toy> parentToys;

    public Parent(String parentName, ParentType type, List<Toy> parentToys) {
        this.parentName = parentName;
        this.type = type;
        this.parentToys = parentToys;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<Toy> getParentToys() {
        return parentToys;
    }

    public void setParentToys(List<Toy> parentToys) {
        this.parentToys = parentToys;
    }

}
