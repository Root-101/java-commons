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
package dev.root101.clean.core.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * La idea es almacenar todas las excepciones o similar como un stack trace en
 * la bd para acceder remoto. De igual manera ese m�dulo recivir posibles
 * errores del cliente
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class ExceptionHandler {

    private static final List<ExceptionHandlerService> exceptionServices = new ArrayList<>();

    private ExceptionHandler() {
    }

    public static ExceptionHandlerService registerExceptionService(ExceptionHandlerService newService) {
        Objects.requireNonNull(newService, "ExceptionHandlerService can't be null");

        exceptionServices.add(newService);
        
        return newService;
    }

    public static void handleException(Throwable ex) {
        Objects.requireNonNull(ex, "Throwable in handleException can't be null");

        System.out.printf("Handling Exception in CLEAN ExceptionHandler | Type => '%s' | message => '%s'\n", ex.getClass(), ex.getMessage());
        for (ExceptionHandlerService exc : exceptionServices) {
            if (exc.contain(ex)) {
                exc.handleException(ex);
            }
        }
    }

    public static boolean contain(Exception ex) {
        return exceptionServices.stream().anyMatch(excep -> (excep.contain(ex)));
    }
}