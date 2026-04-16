package dev.root101.commons.examples.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.root101.commons.validation.ValidationService;
import jakarta.validation.constraints.Size;

class Main_1_2 {

    public static void main(String[] args) throws Exception {
        ValidationService validationService = ValidationService.basic();

        Parent parent = new Parent("1234");
        validationService.validate(parent);
    }

    public static class Parent {
        private static ObjectMapper om = new ObjectMapper();

        @Size(min = 1, max = 5)
        private String parentName;

        public Parent(String parentName) {
            this.parentName = parentName;
        }

        public @Size(min = 1, max = 5) String getParentName() {
            return parentName;
        }

        public void setParentName(@Size(min = 1, max = 5) String parentName) {
            this.parentName = parentName;
        }
    }
}
