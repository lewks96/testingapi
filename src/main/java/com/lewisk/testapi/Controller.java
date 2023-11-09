package com.lewisk.testapi;

import org.apache.http.client.methods.HttpPost;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

@RestController
class ApigeeController {

    private String makeHttpRequest(Config config)
    {
        String clientCredentials = "client_id=" + config.getClientId() + "&client_secret=" + config.getClientSecret();
        String result = "";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(config.getApigeeUrl());

            // add request headers
            request.addHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setEntity(new org.apache.http.entity.StringEntity(clientCredentials));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                    System.out.println(result);
                }

            }
        } catch(SocketTimeoutException timeoutException) {
            String msg = timeoutException.getMessage();
            msg += "Bytes sent: " + timeoutException.bytesTransferred;
            return msg;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @PostMapping("/api")
    public String post(@RequestBody Config config) {
        try {
            return makeHttpRequest(config);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}