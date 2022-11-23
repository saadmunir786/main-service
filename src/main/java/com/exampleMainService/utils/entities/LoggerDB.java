package com.exampleMainService.utils.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "Log")
@Table(name = "logs")
public class LoggerDB {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;
    @Column(name = "timestamp")
    private long timestamp;
    @Column(name = "username")
    private String username;
    @Column(name = "request_url")
    private String requestURL;
    @Column(name = "response_code")
    private int responseCode;
    @Column(name = "response_message")
    private String message;
    @Column(name = "status")
    private String status;
    @Column(name = "meta")
    private String meta;

    public LoggerDB(){
        id = responseCode = 0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.timestamp = timestamp.getTime();
        username = requestURL = message = status = meta = null;
    }

    public LoggerDB(long id, String username, String requestURL, int responseCode, String message, String status, String meta) {
        this.id = id;
        this.username = username;
        this.requestURL = requestURL;
        this.responseCode = responseCode;
        this.message = message;
        this.status = status;
        this.meta = meta;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.timestamp = timestamp.getTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id +"\""+
                ", \"timestamp\":\"" + timestamp +"\""+
                ", \"username\":\"" + username + '\"' +
                ", \"requestURL\":\"" + requestURL + '\"' +
                ", \"responseCode\":\"" + responseCode +"\""+
                ", \"message\":\"" + message + '\"' +
                ", \"status\":\"" + status + '\"' +
                ", \"meta\":" + (meta!=null && meta.contains("{")?meta:(meta!=""?"\""+meta.toString()+"\"":meta)) +
                '}';
    }
}
