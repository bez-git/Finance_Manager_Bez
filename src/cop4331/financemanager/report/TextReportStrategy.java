package cop4331.financemanager.report;

import cop4331.financemanager.model.Transaction;
import cop4331.financemanager.model.TransactionType;

import java.util.List;

/**
 * Plain-text implementation of {@link ReportStrategy}.
 * <p>
 * Produces a short human-readable summary with counts and totals
 * for income, expense, and overall balance.
 */
public class TextReportStrategy implements ReportStrategy {

    /**
     * Exports a simple text summary of the given transactions.
     *
     * @param data list of transactions to summarize
     * @return multi-line text with totals and balance
     */
    @Override
    public String export(List<Transaction> data) {
        double income = 0;
        double expense = 0;
        for (Transaction t : data) {
            if (t.getType() == TransactionType.INCOME) {
                income += t.getAmount();
            } else {
                expense += t.getAmount();
            }
        }
        double balance = income - expense;

        return "Transactions: " + data.size() + System.lineSeparator() +
               "Total income: " + income + System.lineSeparator() +
               "Total expense: " + expense + System.lineSeparator() +
               "Balance: " + balance + System.lineSeparator();
    }
}
