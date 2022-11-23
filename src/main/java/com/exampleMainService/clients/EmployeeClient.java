package com.exampleMainService.clients;

import com.exampleMainService.utils.entities.LoggerDB;
import com.exampleMainService.utils.services.LoggerDBService;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
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
import java.util.List;

@Component
public class EmployeeClient {
    private CloseableHttpClient client = HttpClients.createDefault();
    @Value("${url}")
    private String URL;
    Logger logger = LoggerFactory.getLogger(EmployeeClient.class);
    @Autowired
    LoggerDBService loggerDBService;

    public String saveEmployee(String json, String username){
        String responseBody = null;
        LoggerDB loggerDB = new LoggerDB();
        loggerDB.setUsername(username);
        loggerDB.setMeta(json);

        try {
            HttpPut request = new HttpPut(URL + "/employee/save");
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
            JSONParser parser = new JSONParser(responseBody);
            LinkedHashMap<String, Object> jsonEmployee = parser.parseObject();
            if(jsonEmployee.get("id").toString().equals("0")){
                loggerDB.setStatus("Fail");
                loggerDB.setMessage("Duplicate entry of Employee email OR Department not exists against the provided department id.");
            }
            else {
                loggerDB.setStatus("Success");
                loggerDB.setMessage("Employee saved.");
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
    public String getEmployees(String username){
        String responseBody = null;
        LoggerDB loggerDB = new LoggerDB();
        loggerDB.setUsername(username);
        loggerDB.setMeta("");

        try {
            HttpGet request = new HttpGet(URL + "/employee/get");
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
            List<Object> jsonEmployee = parser.list();

            if(jsonEmployee.size()==0){
                loggerDB.setStatus("Fail");
                loggerDB.setMessage("Empty List OR another error occur while getting list of employees. ");
            }
            else {
                loggerDB.setStatus("Success");
                loggerDB.setMessage("List of Employees received.");
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
    public String getEmployeeById(long id, String username){
        String responseBody = null;
        LoggerDB loggerDB = new LoggerDB();
        loggerDB.setUsername(username);
        loggerDB.setMeta("id="+id);

        try {
            HttpGet request = new HttpGet(URL + "/employee/get/"+id);
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
            if(json.get("id").toString().equals("0")){
                loggerDB.setStatus("Fail");
                loggerDB.setMessage("No employee exists against the provided employee ID: "+id);
            }
            else {
                loggerDB.setStatus("Success");
                loggerDB.setMessage("Employee record is received against employee ID: "+id);
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
    public String updateEmployee(String json, String username){
        String responseBody = null;
        LoggerDB loggerDB = new LoggerDB();
        loggerDB.setUsername(username);
        loggerDB.setMeta(json);

        try {
            HttpPut request = new HttpPut(URL + "/employee/update");
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
            JSONParser parser = new JSONParser(responseBody);
            LinkedHashMap<String, Object> jsonObject = parser.parseObject();
            if(jsonObject.get("id").toString().equals("0")){
                loggerDB.setStatus("Fail");
                loggerDB.setMessage("Duplicate entry of Employee email OR No record exists against the provided id.");
            }
            else {
                loggerDB.setStatus("Success");
                loggerDB.setMessage("Employee updated.");
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
    public String deleteEmployee(long id, String username){
        String responseBody = null;
        LoggerDB loggerDB = new LoggerDB();
        loggerDB.setUsername(username);
        loggerDB.setMeta("id="+id);

        try {
            HttpGet request = new HttpGet(URL + "/employee/delete/"+id);
            request.setHeader("Content-Type", "application/json");
            loggerDB.setRequestURL(request.getURI().toString());

            ResponseHandler<String> responseHandler = (response) -> {
                int status = response.getStatusLine().getStatusCode();
                loggerDB.setResponseCode(status);
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                }else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            responseBody = client.execute(request, responseHandler);
            if(responseBody.contains("Employee is not")){
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
    public String getEmployeeByDepartmentName(String name, String username){
        String responseBody = null;
        LoggerDB loggerDB = new LoggerDB();
        loggerDB.setUsername(username);
        loggerDB.setMeta("Department Name="+name);

        try {
            HttpGet request = new HttpGet(URL + "/employee/getByDepartment/"+name);
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
            List<Object> jsonEmployee = parser.list();

            if(jsonEmployee.size() == 0){
                loggerDB.setStatus("Fail");
                loggerDB.setMessage("No employee exists against the provided department name: "+name);
            }
            else {
                loggerDB.setStatus("Success");
                loggerDB.setMessage("Employee records is received against department name: "+name);
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
    public String getEmployeeByPagination(int page, int size, String username){
        String responseBody = null;
        LoggerDB loggerDB = new LoggerDB();
        loggerDB.setUsername(username);
        loggerDB.setMeta("Page No="+page+"&Size="+size);

        try {
            HttpGet request = new HttpGet(URL + "/employee/getByPagination?page="+page+"&size="+size);
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
            List<Object> jsonEmployee = parser.list();

            if(jsonEmployee.size() == 0){
                loggerDB.setStatus("Fail");
                loggerDB.setMessage("No employee exists.");
            }
            else {
                loggerDB.setStatus("Success");
                loggerDB.setMessage("Employee records is received with page no: "+page+" and size: "+size);
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
}
