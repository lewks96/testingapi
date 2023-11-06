package com.lewisk.testapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
class ApigeeController {

    private String makeHttpRequest(Config config)
    {
        String clientCredentials = "client_id=" + config.getClientId() + "&client_secret=" + config.getClientSecret();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(clientCredentials))
                .uri(URI.create(config.getApigeeUrl()))
                .build();
        try {
            long startTime = System.currentTimeMillis();
            HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            long endTime = System.currentTimeMillis();
            return "StatusCode: " + response.statusCode() + "\nBody: " + response.body() + "\nTime (ms): " + (endTime - startTime);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/api")
    public String get() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String text = Files.readString(Path.of("/tmp/apiconfig.json"));
            Config cfg = mapper.readValue(text, Config.class);
            return makeHttpRequest(cfg);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
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