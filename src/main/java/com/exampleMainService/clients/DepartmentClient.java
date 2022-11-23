package com.exampleMainService.clients;

import com.exampleMainService.utils.entities.LoggerDB;
import com.exampleMainService.utils.services.LoggerDBService;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class DepartmentClient {
    private CloseableHttpClient client = HttpClients.createDefault();
    @Value("${url}")
    private String URL;
    Logger logger = LoggerFactory.getLogger(DepartmentClient.class);
    @Autowired
    LoggerDBService loggerDBService;

    public String saveDepartment(String json, String username){
        String responseBody = null;
        LoggerDB loggerDB = new LoggerDB();
        loggerDB.setUsername(username);
        loggerDB.setMeta(json);

        try {
            HttpPut request = new HttpPut(URL + "/department/save");
            request.setHeader("Content-Type", "application/json");
            StringEntity jsonEntity = new StringEntity(json);
            request.setEntity(jsonEntity);
            loggerDB.setRequestURL(request.getURI().toString());

            ResponseHandler<String> responseHandler = (response) -> {
                int status = response.getStatusLine().getStatusCode();
                loggerDB.setResponseCode(status);
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            responseBody = client.execute(request, responseHandler);
            if(responseBody == null || responseBody.equals("")){
                loggerDB.setStatus("Fail");
                loggerDB.setMessage("Duplicate entry of Department name OR another error occur.");
            }
            else {
                loggerDB.setStatus("Success");
                loggerDB.setMessage("Department saved.");
            }
        }catch(Exception ex){
            logger.error("Error: "+ex.getMessage());
            loggerDB.setStatus("Fail");
            loggerDB.setMessage(ex.getMessage());
        }
        logger.info(loggerDB.toString());
        loggerDBService.log(loggerDB);
        return responseBody;
    }
    public String getDepartment(String username){
        String responseBody = null;
        LoggerDB loggerDB = new LoggerDB();
        loggerDB.setUsername(username);
        loggerDB.setMeta("");

        try {
            HttpGet request = new HttpGet(URL + "/department/get");
            request.setHeader("Content-Type", "application/json");
            loggerDB.setRequestURL(request.getURI().toString());

            ResponseHandler<String> responseHandler = (response) -> {
                int status = response.getStatusLine().getStatusCode();
                loggerDB.setResponseCode(status);
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            responseBody = client.execute(request, responseHandler);
            if(responseBody == null || responseBody.equals("")){
                loggerDB.setStatus("Fail");
                loggerDB.setMessage("Error occur while getting list of departments.");
            }
            else {
                loggerDB.setStatus("Success");
                loggerDB.setMessage("List of Departments received.");
            }
        }catch(Exception ex){
            logger.error("Error: "+ex.getMessage());
            loggerDB.setStatus("Fail");
            loggerDB.setMessage(ex.getMessage());
        }
        logger.info(loggerDB.toString());
        loggerDBService.log(loggerDB);
        return responseBody;
    }
    public String getDepartmentByName(String name, String username){
        String responseBody = null;
        LoggerDB loggerDB = new LoggerDB();
        loggerDB.setUsername(username);
        loggerDB.setMeta("name="+name);

        try {
            HttpGet request = new HttpGet(URL + "/department/getByName/"+name);
            request.setHeader("Content-Type", "application/json");
            loggerDB.setRequestURL(request.getURI().toString());

            ResponseHandler<String> responseHandler = (response) -> {
                int status = response.getStatusLine().getStatusCode();
                loggerDB.setResponseCode(status);
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            responseBody = client.execute(request, responseHandler);
            if(responseBody == null || responseBody.equals("")){
                loggerDB.setStatus("Fail");
                loggerDB.setMessage("No department exists against the provided department name: "+name);
            }
            else {
                loggerDB.setStatus("Success");
                loggerDB.setMessage("Department record is received against department name: "+name);
            }
        }catch(Exception ex){
            logger.error("Error: "+ex.getMessage());
            loggerDB.setStatus("Fail");
            loggerDB.setMessage(ex.getMessage());
        }
        logger.info(loggerDB.toString());
        loggerDBService.log(loggerDB);
        return responseBody;
    }
    public String getDepartmentById(long id, String username){
        String responseBody = null;
        LoggerDB loggerDB = new LoggerDB();
        loggerDB.setUsername(username);
        loggerDB.setMeta("id="+id);

        try {
            HttpGet request = new HttpGet(URL + "/department/get/"+id);
            request.setHeader("Content-Type", "application/json");
            loggerDB.setRequestURL(request.getURI().toString());

            ResponseHandler<String> responseHandler = (response) -> {
                int status = response.getStatusLine().getStatusCode();
                loggerDB.setResponseCode(status);
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            responseBody = client.execute(request, responseHandler);
            JSONParser parser = new JSONParser(responseBody);
            LinkedHashMap<String, Object> json = parser.parseObject();
            if(json.get("name") == null){
                loggerDB.setStatus("Fail");
                loggerDB.setMessage("No department exists against the provided department ID: "+id);
            }
            else {
                loggerDB.setStatus("Success");
                loggerDB.setMessage("Department record is received against department ID: "+id);
            }
        }catch(Exception ex){
            logger.error("Error: "+ex.getMessage());
            loggerDB.setStatus("Fail");
            loggerDB.setMessage(ex.getMessage());
        }
        logger.info(loggerDB.toString());
        loggerDBService.log(loggerDB);
        return responseBody;
    }
    public String updateDepartment(String json, String username){
        String responseBody = null;
        LoggerDB loggerDB = new LoggerDB();
        loggerDB.setUsername(username);
        loggerDB.setMeta(json);

        try {
            HttpPut request = new HttpPut(URL + "/department/update");
            request.setHeader("Content-Type", "application/json");
            StringEntity jsonEntity = new StringEntity(json);
            request.setEntity(jsonEntity);
            loggerDB.setRequestURL(request.getURI().toString());

            ResponseHandler<String> responseHandler = (response) -> {
                int status = response.getStatusLine().getStatusCode();
                loggerDB.setResponseCode(status);
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            responseBody = client.execute(request, responseHandler);
            if(responseBody == null || responseBody.equals("")){
                loggerDB.setStatus("Fail");
                loggerDB.setMessage("Duplicate entry of Department name OR No record exists against the provided id.");
            }
            else {
                loggerDB.setStatus("Success");
                loggerDB.setMessage("Department updated.");
            }
        }catch(Exception ex){
            logger.error("Error: "+ex.getMessage());
            loggerDB.setStatus("Fail");
            loggerDB.setMessage(ex.getMessage());
        }
        logger.info(loggerDB.toString());
        loggerDBService.log(loggerDB);
        return responseBody;
    }
    public String deleteDepartment(long id, String username){
        String responseBody = null;
        LoggerDB loggerDB = new LoggerDB();
        loggerDB.setUsername(username);
        loggerDB.setMeta("id="+id);

        try {
            HttpDelete request = new HttpDelete(URL + "/department/delete/"+id);
            request.setHeader("Content-Type", "application/json");
            loggerDB.setRequestURL(request.getURI().toString());

            ResponseHandler<String> responseHandler = (response) -> {
                int status = response.getStatusLine().getStatusCode();
                loggerDB.setResponseCode(status);
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else if (status == 500) {
                    throw new ClientProtocolException("Department can't be deleted due to foreign key constraint.");
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            responseBody = client.execute(request, responseHandler);
            if(responseBody.contains("Department not")){
                loggerDB.setStatus("Fail");
                loggerDB.setMessage(responseBody);
            }
            else {
                loggerDB.setStatus("Success");
                loggerDB.setMessage(responseBody);
            }
        }catch(Exception ex){
            logger.error("Error: "+ex.getMessage());
            loggerDB.setStatus("Fail");
            loggerDB.setMessage(ex.getMessage());
            responseBody = ex.getMessage();
        }
        logger.info(loggerDB.toString());
        loggerDBService.log(loggerDB);
        return responseBody;
    }
}
