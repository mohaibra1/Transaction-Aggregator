package aggregator;

import aggregator.Transaction;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class AggregateController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final RemoteCallService remoteCallService;

    public AggregateController(RemoteCallService remoteCallService) {
        this.remoteCallService = remoteCallService;
    }

    @GetMapping("/aggregate")
    @Cacheable(cacheNames = "transactions", key = "#account")
    public List<Transaction> aggregate(@RequestParam String account) throws ExecutionException, InterruptedException {
        String url1 = "http://localhost:8888/transactions?account=" + account;
        String url2 = "http://localhost:8889/transactions?account=" + account;

        var future1 = remoteCallService.fetchAsync(url1);
        var future2 = remoteCallService.fetchAsync(url2);

        CompletableFuture.allOf(future1, future2).join();

        List<Transaction> allTransactions = new ArrayList<>();

        allTransactions.addAll(future1.get());
        allTransactions.addAll(future2.get());


        // Sort by timestamp (Descending)
        allTransactions.sort(Comparator.comparing(Transaction::getTimestamp).reversed());

        return allTransactions;
    }

}