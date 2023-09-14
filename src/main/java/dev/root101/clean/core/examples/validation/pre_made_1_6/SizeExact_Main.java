package dev.root101.clean.core.examples.validation.pre_made_1_6;

import dev.root101.clean.core.utils.validation.ValidationService;
import dev.root101.clean.core.utils.validation.annotations.SizeExact;

public class SizeExact_Main {

    public static void main(String[] args) throws Exception {
        record Parent(
                @SizeExact(length = 9)
                String ci) {

        }

        Parent parent = new Parent("123546789000");
        ValidationService.validateAndThrow(parent);
    }

}
