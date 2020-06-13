package com.clean.core.utils.validation.annotations;

import com.clean.core.utils.validation.ValidationResult;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 * @param <T> Class type for the annotation
 * @param <K> Class type for the source value
 */
public interface AnnotationRegister<T, K> {

    public void checkAnnotation(T annotation, K source, ValidationResult validationResult) throws IllegalArgumentException, IllegalAccessException;
}
