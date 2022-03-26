/*
 * Copyright 2021 Root101 (jhernandezb96@gmail.com, +53-5-426-8660).
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
package dev.root101.clean.core.exceptions;

import dev.root101.clean.core.utils.validation.ValidationMessage;
import dev.root101.clean.core.utils.validation.ValidationResult;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @author jjhurtado@Github
 */
public class ValidationException extends IllegalArgumentException {

    private final ValidationResult validationErrors;

    public ValidationException(String string) {
        this(null, string);
    }

    public ValidationException(String source, String message) {
        super(message);
        this.validationErrors = ValidationResult.build();
        this.validationErrors.add(ValidationMessage.from(source, message));
    }

    public ValidationException(ValidationResult validationErrors) {
        this.validationErrors = validationErrors;
    }

    public ValidationResult getValidationErrors() {
        return validationErrors;
    }

    @Override
    public String getMessage() {
        return validationErrors.toString();
    }
}
