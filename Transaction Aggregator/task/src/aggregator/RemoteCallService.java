package aggregator;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class RemoteCallService {

    private RestTemplate restTemplate = new RestTemplate();

    @Async
    public CompletableFuture<List<Transaction>> fetchAsync(String url) {
        int maxRetries = 5;
        for (int i = 0; i < maxRetries; i++) {
            try {
                Transaction[] data = restTemplate.getForObject(url, Transaction[].class);
                List<Transaction> list =  data != null ? Arrays.asList(data) : Collections.emptyList();
                return CompletableFuture.completedFuture(list);
            } catch (HttpStatusCodeException e) {
                int statusCode = e.getStatusCode().value();
                // Check for 503 (Service Unavailable) or 529 (Too Many Requests)
                if (statusCode == 503) {
                    System.out.println("Response code" + statusCode + " 503 SERVICE_UNAVAILABLE");
                    continue; // Loop again
                }if(statusCode == 529){
                    System.out.println("Response code" + statusCode + " TOO MANY REQUESTS");
                    continue; // Loop again
                }
                // If it's a different error (like 404), don't retry, just return empty
                break;
            } catch (Exception e) {
                // For connection timeouts or other network issues
                break;
            }
        }
        return CompletableFuture.completedFuture(Collections.emptyList());
    }
}
