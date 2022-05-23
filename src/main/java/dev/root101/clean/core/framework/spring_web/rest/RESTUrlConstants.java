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
package dev.root101.clean.core.framework.spring_web.rest;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class RESTUrlConstants {

    public static final String CREATE_PATH = "/create";
    public static final RequestMethod CREATE_METHOD = RequestMethod.POST;

    public static final String EDIT_PATH = "/edit";
    public static final RequestMethod EDIT_METHOD = RequestMethod.POST;

    public static final String DESTROY_PATH = "/destroy";
    public static final RequestMethod DESTROY_METHOD = RequestMethod.POST;

    public static final String DESTROY_ID_PATH = "/destroy_id";
    public static final RequestMethod DESTROY_ID_METHOD = RequestMethod.POST;

    public static final String FIND_ALL_PATH = "/find_all";
    public static final RequestMethod DFIND_ALL_METHOD = RequestMethod.GET;

    public static final String ID = "id";
    public static final String FIND_BY_PATH = "/find_by/{" + ID + "}";
    public static final RequestMethod FIND_BY_METHOD = RequestMethod.GET;

    public static final String COUNT_PATH = "/count";
    public static final RequestMethod COUNT_METHOD = RequestMethod.GET;
}
