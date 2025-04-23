package dev.root101.commons.examples.validation.pre_made_1_6;

import dev.root101.commons.validation.ValidationService;
import dev.root101.commons.validation.annotations.SizeExact;

public class SizeExact_Main {

    public static void main(String[] args) throws Exception {
        ValidationService validationService = ValidationService.basic();
        
        record Parent(
                @SizeExact(length = 9)
                String ci) {

        }

        Parent parent = new Parent("123546789000");
        validationService.validate(parent);
    }

}
