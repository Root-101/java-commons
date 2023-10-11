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
package dev.root101.clean.core.rest;

import dev.root101.clean.core.utils.Jackson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdez960717@Github
 */
public class RestTemplateUtils {

    //----------------------------------- LIST -----------------------------------\\
    public static <T> List<T> getForList(RestOperations template, String url, Class<T> clazz) throws RuntimeException {
        return getForList(template, url, new HashMap<>(), clazz);
    }

    public static <T> List<T> getForList(RestOperations template, String url, Map<String, Object> uriVariables, Class<T> clazz) throws RuntimeException {
        return objectForList(template, url, HttpMethod.GET, uriVariables, clazz);
    }

    public static <T> List<T> postForList(RestOperations template, String url, Class<T> clazz) throws RuntimeException {
        return postForList(template, url, new HashMap<>(), clazz);
    }

    public static <T> List<T> postForList(RestOperations template, String url, Map<String, Object> uriVariables, Class<T> clazz) throws RuntimeException {
        return objectForList(template, url, HttpMethod.POST, uriVariables, clazz);
    }

    public static <T> List<T> objectForList(RestOperations template, String url, HttpMethod method, Map<String, Object> uriVariables, Class<T> clazz) throws RuntimeException {
        ParameterizedTypeReference<List<T>> type = new ParameterizedTypeReference<List<T>>() {
        };
        ResponseEntity<List<T>> response = template.exchange(url, method, null, type, uriVariables);

        //como es tipo <T> lo que lee es linkedHashMap, hay que castearlo de nuevo al objeto como tal
        try {
            return Jackson.convert(response.getBody(), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //----------------------------------- MAP -----------------------------------\\
    public static <V, K> Map<V, K> postForMap(RestOperations template, String url, Class<V> clazzV, Class<K> clazzK) throws RuntimeException {
        return postForMap(template, url, new HashMap<>(), clazzV, clazzK);
    }

    public static <V, K> Map<V, K> postForMap(RestOperations template, String url, Map<String, Object> uriVariables, Class<V> clazzV, Class<K> clazzK) throws RuntimeException {
        return objectForMap(template, url, HttpMethod.POST, uriVariables, clazzV, clazzK);
    }

    public static <V, K> Map<V, K> getForMap(RestOperations template, String url, Class<V> clazzV, Class<K> clazzK) throws RuntimeException {
        return getForMap(template, url, new HashMap<>(), clazzV, clazzK);
    }

    public static <V, K> Map<V, K> getForMap(RestOperations template, String url, Map<String, Object> uriVariables, Class<V> clazzV, Class<K> clazzK) throws RuntimeException {
        return objectForMap(template, url, HttpMethod.GET, uriVariables, clazzV, clazzK);
    }

    public static <V, K> Map<V, K> objectForMap(RestOperations template, String url, HttpMethod method, Map<String, Object> uriVariables, Class<V> clazzV, Class<K> clazzK) throws RuntimeException {
        ParameterizedTypeReference<Map<V, K>> type = new ParameterizedTypeReference<Map<V, K>>() {
        };
        ResponseEntity<Map<V, K>> response = template.exchange(url, method, null, type, uriVariables);

        //como es tipo <T> lo que lee es linkedHashMap, hay que castearlo de nuevo al objeto como tal
        //return ConverterHandler.convert(response.getBody(), clazz);
        return response.getBody();
    }
}
