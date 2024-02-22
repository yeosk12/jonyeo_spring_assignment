package spring.jonyeo.topic;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class ExternalApiService {

    private static final String BASE_URL = "https://jsonplaceholder.org";

    public Map<String, Object> getExtDetails(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "/users/" + id;
        return restTemplate.getForObject(url, Map.class);
    }
    
    public String extractDetails(Map<String, Object> extDetails) {
        return (String) extDetails.get("firstname");
    }
}
