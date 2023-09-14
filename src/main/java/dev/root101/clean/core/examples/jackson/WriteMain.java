package dev.root101.clean.core.examples.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.root101.clean.core.utils.Jackson;

public class WriteMain {

    public static void main(String[] args) throws Exception {
        record Parent(
                @JsonProperty("parent_name")
                String parentName) {

        }

        Parent object = new Parent("Pepito Simple");

        String converted = Jackson.toString(object);

        System.out.println(converted);
    }
}
