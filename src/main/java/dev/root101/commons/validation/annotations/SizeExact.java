/*
 * Copyright 2022 Root101 (jhernandezb96@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Or read it directly from LICENCE.txt file at the root of this project.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.root101.commons.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * * The annotated element must not have a sizeequal to 'length'. Supported
 * types are:
 * <ul>
 * <li>{@code CharSequence} (length of char sequence is evaluated)</li>
 * <li>{@code Collection} (collection size is evaluated)</li>
 * <li>{@code Map} (map size is evaluated)</li>
 * <li>Array (array length is evaluated)</li>
 * </ul>
 *
 * @author Root101 (jhernandezb96@gmail.com)
 * @author JesusHdez960717@Github
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SizeExactRegister_CharSequence.class, SizeExactRegister_CollectionString.class, SizeExactRegister_ArrayString.class, SizeExactRegister_MapString.class})
public @interface SizeExact {

    int length();

    String message() default "Size dont match specific length.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String detailMessage() default "";

}
