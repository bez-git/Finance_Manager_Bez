package cop4331.financemanager.report;

import cop4331.financemanager.model.Transaction;

import java.util.List;

/**
 * Strategy interface for exporting transaction reports (Strategy pattern).
 */
public interface ReportStrategy {

    /**
     * Exports the given transactions as a String representation.
     *
     * @param data list of transactions to export
     * @return textual representation of the report
     */
    String export(List<Transaction> data);
}
