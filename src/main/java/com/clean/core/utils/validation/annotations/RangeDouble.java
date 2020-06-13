package com.clean.core.utils.validation.annotations;

import com.clean.core.utils.Severity;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RangeDouble {

    public double low() default Integer.MIN_VALUE;

    public double higth() default Integer.MAX_VALUE;

    public String message() default "";

    public String detailMessage() default "";

    public Severity severity() default Severity.WARNING;

}
