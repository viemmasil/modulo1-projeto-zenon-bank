import br.com.zenon.fraud.Transaction;
import br.com.zenon.fraud.TransactionIngestor;
import br.com.zenon.fraud.TransactionSqlRepository;

void main() {
    var transactionIngestor = new TransactionIngestor();
    List<Transaction> transactions = transactionIngestor.readFile("data/kaggle_log.csv", 10_000);

    long startTime = System.nanoTime();
    var sqlRepository = new TransactionSqlRepository();
    sqlRepository.saveInBatches(transactions, 2500);
    IO.println("Time taken to save all transactions to the database: %d ms".formatted((System.nanoTime() - startTime) / 1_000_000));
    var result = sqlRepository.findByOriginName("C1231006815");
    result.ifPresentOrElse(System.out::println, () -> IO.println("Transaction not found"));
    result = sqlRepository.findByOriginName("C12345");
    result.ifPresentOrElse(System.out::println, () -> IO.println("Transaction not found"));
}