package com.exampleMainService.utils.entities;

public class ResponseWrapper {
    private int code;
    private String message;

    public ResponseWrapper(){}
    public ResponseWrapper(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
