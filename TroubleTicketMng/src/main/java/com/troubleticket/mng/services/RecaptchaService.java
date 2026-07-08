package com.troubleticket.mng.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class RecaptchaService {
    @Value("${google.recaptcha.secret}")
    private String secretKey;

    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean verify(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = VERIFY_URL + "?secret=" + secretKey + "&response=" + token;
        
        Map<String, Object> response = restTemplate.postForObject(url, null, Map.class);
        return response != null && (Boolean) response.get("success");
    }
}