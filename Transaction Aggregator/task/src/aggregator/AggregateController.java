package aggregator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class AggregateController {

    //1. create the /aggregate endpoint
    @GetMapping("/aggregate")
    public String index() {
        //2. Initialize the tool to make external calls
        RestTemplate restTemplate = new RestTemplate();

        //3. Define the target address
        String url = "http://localhost:8889/ping";

        //4. Call the other server and get the plain text response
        String response = restTemplate.getForObject(url, String.class);

        //5. Return that exact string to your client
        return response;
    }
}
