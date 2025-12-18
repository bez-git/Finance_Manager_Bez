package cop4331.financemanager.view;

import cop4331.financemanager.model.FinanceModel;
import cop4331.financemanager.model.Transaction;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Table model that reads data directly from {@link FinanceModel}.
 * <p>
 * It does NOT store its own list of {@link Transaction}
 * (matches instructor feedback)
 */
public class TransactionTableModel extends AbstractTableModel {

    private final FinanceModel model;

    private static final String[] COLUMNS = {
            "ID", "Date", "Type", "Category", "Amount", "Note"
    };

    /**
     * @param model finance model used as backing data source
     */
    public TransactionTableModel(FinanceModel model) {
        this.model = model;
    }

    @Override
    public int getRowCount() {
        return model.getAllTransactions().size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<Transaction> txs = model.getAllTransactions();
        Transaction t = txs.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> t.getId();
            case 1 -> t.getDate();
            case 2 -> t.getType();
            case 3 -> t.getCategory().getName();
            case 4 -> t.getAmount();
            case 5 -> t.getNote();
            default -> null;
        };
    }

    /**
     * Returns the transaction at the given table row.
     *
     * @param row row index (0-based)
     * @return transaction at that row, or {@code null} if out of range
     */
    public Transaction getTransactionAtRow(int row) {
        if (row < 0 || row >= getRowCount()) {
            return null;
        }
        return model.getAllTransactions().get(row);
    }

    /**
     * Notifies Swing that all table data has changed.
     * Called by {@link TransactionTablePanel} when the model updates.
     */
    public void refresh() {
        fireTableDataChanged();
    }
}
