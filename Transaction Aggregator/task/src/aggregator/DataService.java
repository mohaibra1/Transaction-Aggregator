package aggregator;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DataService {
    private TransactionDao transactionDao;

    public DataService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Async
    @Cacheable(cacheNames = "transactions", key = "#account")
    public CompletableFuture<List<Transaction>> getTransactions(String account) throws IncompatibleClassChangeError{
        try { Thread.sleep(1000); } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        List<Transaction> transactions = transactionDao.getTransactions()
                .stream()
                .filter(t -> t.getAccount().equals(account))
                .toList();
        return CompletableFuture.completedFuture(transactions);
    }

//    public List<Transaction> updateTransactions(String account, List<Transaction> transactions) {
//        return dataDao.updateTransactions(account, transactions);
//    }
}
