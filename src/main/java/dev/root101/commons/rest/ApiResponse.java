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

import org.springframework.http.ResponseEntity;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdez960717@Github
 * @param <T>
 */
public class ApiResponse<T> {

    public static <T> ApiResponse<T> build(ResponseEntity<T> response) {
        return new ApiResponse<>(
                String.valueOf(response.getStatusCode().value()),
                response.getStatusCode().toString(),
                response.getBody()
        );
    }

    public static <T> ApiResponse<T> build(String status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.success(null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("200", "Success", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("200", message, data);
    }

    private String status;
    private String message;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(String status, String msg, T data) {
        this.status = status;
        this.message = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
