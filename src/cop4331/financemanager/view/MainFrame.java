package cop4331.financemanager.view;

import cop4331.financemanager.controller.AddExpenseController;
import cop4331.financemanager.controller.AddIncomeController;
import cop4331.financemanager.controller.DeleteTransactionController;
import cop4331.financemanager.controller.EditTransactionController;
import cop4331.financemanager.controller.ViewSummaryController;
import cop4331.financemanager.model.FinanceModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Main application window (View).
 * <p>
 * Wires the {@link FinanceModel} to the Swing UI and all controllers.
 */
public class MainFrame extends JFrame implements ChangeListener {

    private final FinanceModel model;
    private final TransactionTablePanel tablePanel;
    private final TransactionFormDialog formDialog;
    private final SummaryDialog summaryDialog;

    // Buttons as fields instead of client properties
    private JButton addIncomeBtn;
    private JButton addExpenseBtn;
    private JButton editBtn;
    private JButton deleteBtn;
    private JButton summaryBtn;
    private JButton saveBtn;

    /**
     * Creates the main window and registers as a {@link ChangeListener}
     * to keep the table in sync with the model.
     *
     * @param model non-null finance model
     */
    public MainFrame(FinanceModel model) {
        super("Finance Manager");
        this.model = model;
        this.model.addChangeListener(this);

        this.tablePanel = new TransactionTablePanel(model);
        this.formDialog = new TransactionFormDialog(this);
        this.summaryDialog = new SummaryDialog(this);

        buildUI();
        wireControllers();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Builds the Swing layout and bottom button bar.
     */
    private void buildUI() {
        setLayout(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);

        JPanel buttons = new JPanel();

        addIncomeBtn = new JButton("Add Income");
        addExpenseBtn = new JButton("Add Expense");
        editBtn = new JButton("Edit");
        deleteBtn = new JButton("Delete");
        summaryBtn = new JButton("View Summary");
        saveBtn = new JButton("Save");

        buttons.add(addIncomeBtn);
        buttons.add(addExpenseBtn);
        buttons.add(editBtn);
        buttons.add(deleteBtn);
        buttons.add(summaryBtn);
        buttons.add(saveBtn);

        add(buttons, BorderLayout.SOUTH);
    }

    /**
     * Connects all buttons to their corresponding controllers.
     */
    private void wireControllers() {
        AddIncomeController addIncomeController =
                new AddIncomeController(model, this, formDialog);
        AddExpenseController addExpenseController =
                new AddExpenseController(model, this, formDialog);
        EditTransactionController editController =
                new EditTransactionController(model, this, formDialog);
        DeleteTransactionController deleteController =
                new DeleteTransactionController(model, this);
        ViewSummaryController summaryController =
                new ViewSummaryController(model, this, summaryDialog);

        addIncomeBtn.addActionListener(addIncomeController);
        addExpenseBtn.addActionListener(addExpenseController);
        editBtn.addActionListener(editController);
        deleteBtn.addActionListener(deleteController);
        summaryBtn.addActionListener(summaryController);

        // Save just calls model.save()
        saveBtn.addActionListener(e -> model.save());
    }

    /**
     * @return the table panel that displays all transactions
     */
    public TransactionTablePanel getTablePanel() {
        return tablePanel;
    }

    /**
     * Called when the model fires a change event.
     * Delegates to the table panel to refresh its contents.
     *
     * @param e change event from the model
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        tablePanel.refreshTable();
    }
}
