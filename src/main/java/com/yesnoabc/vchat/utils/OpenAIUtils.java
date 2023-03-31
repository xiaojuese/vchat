package com.yesnoabc.vchat.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qupeng
 * @Date: 2023/3/29 14:53
 * @Description: null.java
 * @Version:
 * @Last Modified by: qupeng
 * @Last Modified Time: 2023/3/29 14:53
 */


@Component
public class OpenAIUtils {

    @Value("${openai.api.key}")
    private String apiKey;
    @Value("${openai.model}")
    private String model;
    @Value("${openai.role}")
    private String role;

    private final String API_URL = "https://openai.yesnoabc.com/v1/";

    private RestTemplate restTemplate = new RestTemplate();

    public String generateText(String prompt) {
        String endpoint = API_URL + "chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        headers.add("user-agent", "Application");
        Map<String, Object> request = new HashMap<>();
        request.put("model", this.model);
        ArrayList<Map<String, String>> maps = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("role", this.role);
        map.put("content", prompt);
        maps.add(map);


        request.put("messages", maps);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(request, headers);

        return restTemplate.postForObject(endpoint, httpEntity, String.class);
    }
}