package aggregator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
public class AggregateController {
    private final RestTemplate restTemplate =  new RestTemplate();
    //1. create the /aggregate endpoint
    @GetMapping("/aggregate")
    public List<Transaction> aggregate(@RequestParam String account) {
        String url1 = "http://localhost:8888/transactions?account=" + account;
        String url2 = "http://localhost:8889/transactions?account=" + account;

        Transaction[] server1Data = restTemplate.getForObject(url1, Transaction[].class);
        Transaction[] server2Data = restTemplate.getForObject(url2, Transaction[].class);

        List<Transaction> allTransactions = new ArrayList<>();
        if (server1Data != null) {
            allTransactions.addAll(Arrays.asList(server1Data));
        }
        if (server2Data != null) {
            allTransactions.addAll(Arrays.asList(server2Data));
        }

        allTransactions.sort(Comparator.comparing(Transaction::getTimestamp).reversed());

        return allTransactions;
    }
}
