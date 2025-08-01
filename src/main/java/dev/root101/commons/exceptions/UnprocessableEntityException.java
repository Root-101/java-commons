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
package dev.root101.commons.exceptions;

import org.springframework.http.HttpStatus;

;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com)
 * @author JesusHdez960717@Github
 */
public class UnprocessableEntityException extends ApiException {

    public static HttpStatus STATUS = HttpStatus.UNPROCESSABLE_ENTITY;

    public UnprocessableEntityException() {
        super(STATUS, STATUS.getReasonPhrase());
    }

    public UnprocessableEntityException(String message) {
        super(STATUS, message);
    }

}
