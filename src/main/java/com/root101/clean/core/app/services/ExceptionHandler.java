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
package com.root101.clean.core.app.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class ExceptionHandler {

    private static final List<ExceptionHandlerService> exceptionServices = new ArrayList<>();

    static {//set by default any error in this handler
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            System.out.printf("-----EXCEPTION_HANDLER----- > Error on thread '%s' with throwable message '%s'\n", t.getName(), e.getMessage());
            ExceptionHandler.handleException(e);
        });
    }

    private ExceptionHandler() {
    }

    public static void registerExceptionService(ExceptionHandlerService newService) {
        Objects.requireNonNull(newService, "ExceptionHandlerService can't be null");

        exceptionServices.add(newService);
    }

    public static void handleException(Throwable ex) {
        Objects.requireNonNull(ex, "Throwable in handleException can't be null");

        System.out.printf("Handling Exception | Type => '%s' | message => '%s'\n", ex.getClass(), ex.getMessage());
        System.out.println("Stack Trace: " + Arrays.toString(ex.getStackTrace()));
        boolean found = false;
        for (ExceptionHandlerService exc : exceptionServices) {
            if (exc.contain(ex)) {
                exc.handleException(ex);
                found = true;
            }
        }
        //si no se encontro y no es una Exception generica, la convierto a generica y la proceso
        if (!found && !ex.getClass().toString().equals(Exception.class.toString())) {
            handleException(new Exception(ex));
        }
    }

    public static boolean contain(Class type) {
        return exceptionServices.stream().anyMatch(excep -> (excep.contain(type)));
    }

    public static boolean contain(Exception ex) {
        return exceptionServices.stream().anyMatch(excep -> (excep.contain(ex)));
    }
}
