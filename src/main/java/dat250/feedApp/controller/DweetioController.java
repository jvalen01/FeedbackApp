package dat250.feedApp.controller;

import dat250.feedApp.model.Poll;
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

    private final RestTemplate restTemplate = new RestTemplate();


    /*
    public DweetioController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

     */

    public DweetioController() {

    }

    //@PostMapping("/sendToDweet")
    public Object sendToDweet(Poll poll){ //@RequestBody Map<String, Object> requestBody) {
        String dweetUrl = "https://dweet.io/dweet/for/FeedbackAppDweet";

        // Forward the request to Dweet.io
        return restTemplate.postForObject(dweetUrl, poll, Poll.class);
    }



}
