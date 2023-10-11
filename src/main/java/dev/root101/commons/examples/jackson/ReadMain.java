package dev.root101.commons.examples.jackson;

import dev.root101.commons.utils.Jackson;

public class ReadMain {

    public static void main(String[] args) throws Exception {
        record Parent(
                String parentName) {

        }

        String parentString = """
                              {
                                "parentName": "Pepito Simple"
                              }
                              """;

        Parent converted = Jackson.read(parentString, Parent.class);
        
        System.out.println(converted);
    }
}
