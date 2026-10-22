import br.com.zenon.fraud.TransactionReport;
import br.com.zenon.fraud.TransactionReport.Statistics;

void main(String[] args) {
    System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
    Locale locale = Locale.getDefault();

    if (args.length > 0) {
        locale = switch (args[0]) {
            case "en", "en-us", "en_US" -> Locale.US;
            case "pt", "pt-br", "pt_BR" -> Locale.forLanguageTag("pt-BR");
            default -> Locale.getDefault();
        };
    }
    ResourceBundle bundle = ResourceBundle.getBundle("ZenonResource", locale);
    IO.println("%s: %s".formatted(bundle.getString("locale.using_locale"), locale));
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

    TransactionReport transactionReport = new TransactionReport();
    Statistics statistics = transactionReport.generateReport("data/kaggle_log.csv");
    IO.println("""
            %s: %d
            %s: %d
            %s: %s
            """.formatted(bundle.getString("report.total_lines"), statistics.totalTxn(),
            bundle.getString("report.total_frauds"), statistics.totalFraud(),
            bundle.getString("report.total_amount"), numberFormat.format(statistics.totalAmount())));
}
