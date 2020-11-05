package bts.KCamps.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GoogleCallService {

    private final RestTemplate restTemplate;

    @Value("google.apiKey")
    private String apiKey;

    public String[] getCampCoordinationByAddress(String address) {
        String replace = address.replace(' ', '+');
        String url = "https://www.google.com/maps/embed/v1/search?key=" + "AIzaSyDNH2SfyJ01U43bFcZdJ0TZ7LIk_QZjmzc" + "&q=" + replace;
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity);
        String body = forEntity.getBody();
        int index = body.indexOf("\",null,[null,null,");
        String substring = body.substring(index + 18, index + 18 + 21);
        return substring.split(",");
    }
}
