package cop4331.financemanager.view;

import cop4331.financemanager.model.FinanceModel;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that contains the {@link JTable} showing all transactions.
 */
public class TransactionTablePanel extends JPanel {

    private final JTable table;
    private final TransactionTableModel tableModel;

    /**
     * Creates the panel with a table bound to the given model.
     *
     * @param model finance model used as data source
     */
    public TransactionTablePanel(FinanceModel model) {
        super(new BorderLayout());
        this.tableModel = new TransactionTableModel(model);
        this.table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * @return the ID of the currently selected transaction,
     * or -1 if nothing is selected
     */
    public int getSelectedTransactionId() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return -1;
        }
        TransactionTableModel model = (TransactionTableModel) table.getModel();
        return model.getTransactionAtRow(row).getId();
    }

    /**
     * Refreshes the JTable from the underlying model data.
     * Called when {@link MainFrame} receives a change event.
     */
    public void refreshTable() {
        tableModel.refresh();
    }
}
