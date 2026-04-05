package aggregator;

import aggregator.Transaction;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
public class AggregateController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/aggregate")
    public List<Transaction> aggregate(@RequestParam String account) {
        String url1 = "http://localhost:8888/transactions?account=" + account;
        String url2 = "http://localhost:8889/transactions?account=" + account;

        List<Transaction> allTransactions = new ArrayList<>();

        // Fetch from both servers with retry logic
        allTransactions.addAll(fetchWithRetry(url1));
        allTransactions.addAll(fetchWithRetry(url2));

        // Sort by timestamp (Descending)
        allTransactions.sort(Comparator.comparing(Transaction::getTimestamp).reversed());

        return allTransactions;
    }

    private List<Transaction> fetchWithRetry(String url) {
        int maxRetries = 5;
        for (int i = 0; i < maxRetries; i++) {
            try {
                Transaction[] data = restTemplate.getForObject(url, Transaction[].class);
                return data != null ? Arrays.asList(data) : Collections.emptyList();
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
        return Collections.emptyList();
    }
}