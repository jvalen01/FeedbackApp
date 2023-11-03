package dat250.feedApp.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DweetioController {

    private final RestTemplate restTemplate;

    public DweetioController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostMapping("/sendToDweet")
    public Object sendToDweet(@RequestBody Map<String, Object> requestBody) {
        String dweetUrl = "https://dweet.io/dweet/for/FeedbackAppDweet";

        System.out.println(dweetUrl);
        System.out.println(requestBody);

        // Forward the request to Dweet.io
        return restTemplate.postForObject(dweetUrl, requestBody, Object.class);
    }



}
