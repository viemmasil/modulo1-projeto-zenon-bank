import br.com.zenon.fraud.TransactionReport;
import br.com.zenon.fraud.TransactionReport.Statistics;

void main(String[] args) {
    System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
    var locale = Locale.getDefault();

    if (args.length > 0) {
        locale = switch (args[0]) {
            case "en", "en-us", "en_US" -> Locale.US;
            case "pt", "pt-br", "pt_BR" -> Locale.forLanguageTag("pt-BR");
            default -> Locale.getDefault();
        };
    }
    var bundle = ResourceBundle.getBundle("ZenonResource", locale);
    IO.println("%s: %s".formatted(bundle.getString("locale.using_locale"), locale));
    var intFormatter = NumberFormat.getIntegerInstance(locale);
    var currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    currencyFormatter.setCurrency(Currency.getInstance("USD"));

    var transactionReport = new TransactionReport();
    var statistics = transactionReport.generateReport("data/kaggle_log.csv");
    IO.println("""
            %s: %s
            %s: %s
            %s: %s
            """.formatted(bundle.getString("report.total_lines"), intFormatter.format(statistics.totalTxn()),
            bundle.getString("report.total_frauds"), intFormatter.format(statistics.totalFraud()),
            bundle.getString("report.total_amount"), currencyFormatter.format(statistics.totalAmount())));
}
