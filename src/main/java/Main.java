import br.com.zenon.fraud.TransactionIngestor;

void main() {

    var TransactionIngestor = new TransactionIngestor("data/kaggle_log.csv");

    var transactions = TransactionIngestor.getTransactions();
    for (int i = 0; i < 10; i++) {
        IO.println(transactions.get(i));
    }
}
