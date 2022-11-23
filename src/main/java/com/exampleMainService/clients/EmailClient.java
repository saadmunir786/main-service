package com.exampleMainService.clients;

import com.exampleMainService.utils.services.LoggerDBService;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailClient {

    private CloseableHttpClient client = HttpClients.createDefault();

    Logger logger = LoggerFactory.getLogger(EmailClient.class);
    @Autowired
    LoggerDBService loggerDBService;

    public String sendMail(String json, String URL, String username){
        String responseBody = null;

        try {
            HttpPost request = new HttpPost(URL + "/mail");
            request.setHeader("Content-Type", "application/json");
            StringEntity jsonEntity = new StringEntity(json);
            request.setEntity(jsonEntity);

            ResponseHandler<String> responseHandler = (response) -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            responseBody = client.execute(request, responseHandler);

        }catch(Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return responseBody;
    }
}
