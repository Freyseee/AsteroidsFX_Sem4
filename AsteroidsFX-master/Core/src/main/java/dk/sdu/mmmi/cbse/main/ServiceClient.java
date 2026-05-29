package dk.sdu.mmmi.cbse.main;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public int Score(int points) {
        try {
            String url = "http://localhost:8080/score?point=" + points;
            String response = restTemplate.getForObject(url, String.class);
            return Integer.parseInt(response);
        }
        catch(org.springframework.web.client.ResourceAccessException e){
            System.out.print("Score service not available");
            return 0;
        }
    }

    public boolean submitScore() {
        try {
            String response = restTemplate.postForObject("http://localhost:8080/score/submit", null, String.class);
            return Boolean.parseBoolean(response);
        }
        catch(org.springframework.web.client.ResourceAccessException e){
            System.out.print("Score service not available");
            return false;
        }
    }

    public int getHighscore() {
        try {
            return restTemplate.getForObject("http://localhost:8080/score/highscore", Integer.class);
        }
        catch(org.springframework.web.client.ResourceAccessException e){
            System.out.print("Score service not available");
            return 0;
        }
    }
}