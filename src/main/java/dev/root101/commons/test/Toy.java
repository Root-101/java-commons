/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.root101.commons.test;

import jakarta.validation.constraints.Size;

/**
 *
 * @author Yo
 */
public class Toy {

    @Size(max = 5)
    //@ValidationFieldName("toy_name")
    private String toyName;

    public Toy(String toyName) {
        this.toyName = toyName;
    }

    public String getToyName() {
        return toyName;
    }

    public void setToyName(String toyName) {
        this.toyName = toyName;
    }

}
