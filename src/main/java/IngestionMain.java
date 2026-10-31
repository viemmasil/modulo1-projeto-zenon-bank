import br.com.zenon.fraud.Database;
import br.com.zenon.fraud.EfficientTransactionIngestor;
import br.com.zenon.fraud.Transaction;
import br.com.zenon.fraud.TransactionSqlRepository;

void main() {
    Runtime.getRuntime().addShutdownHook(new Thread(Database::shutdown));

    var efficientIngestor = new EfficientTransactionIngestor();
    var sqlRepository = new TransactionSqlRepository(Database.dataSource());

    /* Consumer<Transaction> saveInDb = sqlRepository::save;
    long startTime = System.nanoTime();
    efficientIngestor.readAsStream("data/kaggle_log.csv", saveInDb);
    long timeTakenMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
    IO.println("Time taken to insert 10K records one by one: " + timeTakenMs + "ms"); */

    Consumer<List<Transaction>> saveInBatches =  txn -> sqlRepository.saveInBatches(txn, 1_000);
    long startTime = System.nanoTime();
    efficientIngestor.readBatch("data/kaggle_log.csv", saveInBatches);
    long timeTakenMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
    IO.println("Time taken to insert 10K records in batches: " + timeTakenMs + "ms");
}
