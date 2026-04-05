package aggregator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class PingController {
    private List<Transaction> transactions = new ArrayList<>();

    public PingController() {
        getTransactions();
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong"; // This is what your aggregate service will receive
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> transactions(@RequestParam String account) {
        if (Math.random() > 0.7) { // 30% chance of failing
            return ResponseEntity.status(503).build();
        }
        List<Transaction> transactions = new ArrayList<>();
        for(int i = 0; i < this.transactions.size(); i++){
            if(this.transactions.get(i).getAccount().equals(account)){
                transactions.add(this.transactions.get(i));
            }
        }

        return ResponseEntity.ok(transactions);
    }

    private List<Transaction> getTransactions() {
        Transaction t = new Transaction();
        t.setId("31969aef-ffbe-413a-8a94-bc920556a0d4");
        t.setAccount("02248");
        t.setAmount("5120");
        t.setTimestamp("2023-12-24T00:02:31.886783206");
        t.setServerId("server-04");
        transactions.add(t);

        Transaction t2 = new Transaction();
        t.setId("dcc57df0-d815-497f-be1d-b3fb419b9bee");
        t.setAccount("02248");
        t.setAmount("4933");
        t.setTimestamp("2023-12-21T10:33:56.886823126");
        t.setServerId("server-04");
        transactions.add(t);

        Transaction t3 = new Transaction();
        t.setId("dcc57df0-d815-497f-be1d-b3fb419b9bee");
        t.setAccount("02248");
        t.setAmount("1025");
        t.setTimestamp("023-12-19T16:56:48.886729416");
        t.setServerId("server-25");
        transactions.add(t);

        return transactions;
    }
}
