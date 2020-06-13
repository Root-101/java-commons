package com.clean.core.utils.validation.annotations;

import com.clean.core.utils.validation.ValidationMessage;
import com.clean.core.utils.validation.ValidationResult;
import com.clean.core.utils.validation.checkables.Checkable;
import com.clean.core.utils.validation.checkables.CheckerFactory;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class RangeShortRegister implements AnnotationRegister<RangeShort, Short> {

    @Override
    public void checkAnnotation(RangeShort annotation, Short source, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException {
        Checkable ch = CheckerFactory.buildRangeCheckable(source, annotation.low(), annotation.higth());
        ValidationMessage msg = ValidationMessage.from(source, annotation.message(), annotation.detailMessage(), annotation.severity());
        validationResult.check(ch, msg);
    }

}
