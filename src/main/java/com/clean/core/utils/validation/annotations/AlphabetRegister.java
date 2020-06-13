package com.clean.core.utils.validation.annotations;

import com.clean.core.utils.validation.ValidationMessage;
import com.clean.core.utils.validation.ValidationResult;
import com.clean.core.utils.validation.checkables.Checkable;
import com.clean.core.utils.validation.checkables.CheckerFactory;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class AlphabetRegister implements AnnotationRegister<Alphabet, Character> {

    @Override
    public void checkAnnotation(Alphabet annotation, Character source, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException {
        Checkable ch = CheckerFactory.buildAlphabetCheckable(source);
        ValidationMessage msg = ValidationMessage.from(source, annotation.message(), annotation.detailMessage(), annotation.severity());
        validationResult.check(ch, msg);
    }

}
