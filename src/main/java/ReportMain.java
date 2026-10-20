import br.com.zenon.fraud.TransactionReport;
import br.com.zenon.fraud.TransactionReport.Statistics;

void main() {
    TransactionReport transactionReport = new TransactionReport();
    Statistics statistics = transactionReport.generateReport("data/kaggle_log.csv");
    IO.println("""
            Total Lines: %d
            Total Frauds: %d
            Total Amount Transacted: %.2f
            """.formatted(statistics.totalTxn(), statistics.totalFraud(), statistics.totalAmount()));
}
