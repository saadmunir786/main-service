package com.exampleMainService.utils.entities;

public class GeneratedStringResponseWrapper {
    private int code;
    private String generatedString;

    public GeneratedStringResponseWrapper(){}
    public GeneratedStringResponseWrapper(int code, String generatedString) {
        this.code = code;
        this.generatedString = generatedString;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getGeneratedString() {
        return generatedString;
    }

    public void setGeneratedString(String generatedString) {
        this.generatedString = generatedString;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "code=" + code +
                ", generatedString='" + generatedString + '\'' +
                '}';
    }
}
