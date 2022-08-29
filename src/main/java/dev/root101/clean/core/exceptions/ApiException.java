/*
 * Copyright 2022 Root101 (jhernandezb96@gmail.com, +53-5-426-8660).
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

import org.springframework.http.HttpStatus;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class ApiException extends RuntimeException {

    private final int rawStatusCode;
    private final String message;

    public ApiException(HttpStatus status) {
        super(status.getReasonPhrase());
        this.rawStatusCode = status.value();
        this.message = status.getReasonPhrase();
    }

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.rawStatusCode = status.value();
        this.message = message;
    }

    public ApiException(int rawStatusCode, String message) {
        super(message);
        this.rawStatusCode = rawStatusCode;
        this.message = message;
    }

    public HttpStatus status() {
        return HttpStatus.resolve(rawStatusCode);
    }

    public int getRawStatusCode() {
        return rawStatusCode;
    }

    public String getReasonPhrase() {
        return message;
    }

}
