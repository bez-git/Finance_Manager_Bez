package cop4331.financemanager.report;

import cop4331.financemanager.model.Transaction;

import java.util.List;

/**
 * CSV-based implementation of {@link ReportStrategy}.
 * <p>
 * Produces a simple comma-separated report with a header line:
 * {@code id,date,amount,type,category,note}
 */
public class CsvReportStrategy implements ReportStrategy {

    /**
     * Exports the given transactions as CSV.
     *
     * @param data list of transactions to export
     * @return CSV string containing a header and one line per transaction
     */
    @Override
    public String export(List<Transaction> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("id,date,amount,type,category,note\n");
        for (Transaction t : data) {
            sb.append(t.getId()).append(',')
              .append(t.getDate()).append(',')
              .append(t.getAmount()).append(',')
              .append(t.getType()).append(',')
              .append(t.getCategory().getName()).append(',')
              .append('"').append(t.getNote().replace("\"", "\"\"")).append('"')
              .append('\n');
        }
        return sb.toString();
    }
}
