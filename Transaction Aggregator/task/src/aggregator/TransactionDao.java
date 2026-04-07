package aggregator;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionDao {

    public List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction t = new Transaction();
        t.setId("31969aef-ffbe-413a-8a94-bc920556a0d4");
        t.setAccount("02248");
        t.setAmount("5120");
        t.setTimestamp("2023-12-24T00:02:31.886783206");
        t.setServerId("server-04");
        transactions.add(t);

        Transaction t2 = new Transaction();
        t2.setId("dcc57df0-d815-497f-be1d-b3fb419b9bee");
        t2.setAccount("02248");
        t2.setAmount("4933");
        t2.setTimestamp("2023-12-21T10:33:56.886823126");
        t2.setServerId("server-04");
        transactions.add(t2);

        Transaction t3 = new Transaction();
        t3.setId("dcc57df0-d815-497f-be1d-b3fb419b9bee");
        t3.setAccount("02248");
        t3.setAmount("1025");
        t3.setTimestamp("023-12-19T16:56:48.886729416");
        t3.setServerId("server-25");
        transactions.add(t3);

        return transactions;
    }
}
