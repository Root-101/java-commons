package com.clean.core.utils.validation.annotations;

import com.clean.core.utils.validation.ValidationMessage;
import com.clean.core.utils.validation.ValidationResult;
import com.clean.core.utils.validation.checkables.Checkable;
import com.clean.core.utils.validation.checkables.CheckerFactory;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class LengthExactRegister implements AnnotationRegister<LengthExact, String> {

    @Override
    public void checkAnnotation(LengthExact annotation, String source, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException {
        Checkable ch = CheckerFactory.buildLengthExactCheckable(source, annotation.length());
        ValidationMessage msg = ValidationMessage.from(source, annotation.message(), annotation.detailMessage(), annotation.severity());
        validationResult.check(ch, msg);
    }

}
