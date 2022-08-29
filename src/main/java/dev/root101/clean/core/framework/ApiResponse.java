package dev.root101.clean.core.framework;

import org.springframework.http.ResponseEntity;

public class ApiResponse<T> {

    public static <T> ApiResponse<T> build(ResponseEntity<T> response) {
        return new ApiResponse<>(
                String.valueOf(response.getStatusCodeValue()),
                response.getStatusCode().toString(),
                response.getBody()
        );
    }

    public static <T> ApiResponse<T> build(String status, String msg, T data) {
        return new ApiResponse<>(status, msg, data);
    }

    public static <T> ApiResponse<T> buildSuccesVoid() {
        return buildSucces(null);
    }

    public static <T> ApiResponse<T> buildSucces(T data) {
        return new ApiResponse<>("200", "Success", data);
    }

    public static <T> ApiResponse<T> buildSucces(String msg, T data) {
        return new ApiResponse<>("200", msg, data);
    }

    private String status;
    private String msg;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
