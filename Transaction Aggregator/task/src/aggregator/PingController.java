package aggregator;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@EnableAsync
@RestController
public class PingController {
    private DataService  dataService;

    public PingController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong"; // This is what your aggregate service will receive
    }

    @GetMapping("/transactions")
    public CompletableFuture<ResponseEntity<List<Transaction>>> transactions(@RequestParam String account) {
        if (Math.random() > 0.7) { // 30% chance of failing
            return CompletableFuture.completedFuture(ResponseEntity.status(503).build());
        }

        long start = System.currentTimeMillis();

        return dataService.getTransactions(account)
                .thenApply(result -> {
                    long duration  = System.currentTimeMillis() - start;
                    System.out.println("Account " + account + " actually finished in " + duration);
                    return ResponseEntity.ok(result);
                });
    }
}
