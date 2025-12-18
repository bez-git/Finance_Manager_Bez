package cop4331.financemanager.view;

import cop4331.financemanager.model.Category;
import cop4331.financemanager.model.Transaction;
import cop4331.financemanager.model.TransactionType;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Dialog used for adding/editing a {@link Transaction}.
 * <p>
 * Follows the state diagram in the design (AddIncomeMode, AddExpenseMode,
 * EditMode, ErrorShown, Hidden).
 */
public class TransactionFormDialog extends JDialog {

    /**
     * Listener interface used by controllers to be notified
     * when the user saves or cancels.
     */
    public interface Listener {

        /**
         * Called when the user clicks Save and validation succeeds.
         *
         * @param tx transaction created from the form fields
         */
        void saveClicked(Transaction tx);

        /**
         * Called when the user cancels the dialog.
         */
        void canceled();
    }

    private final JTextField dateField = new JTextField(10);
    private final JTextField amountField = new JTextField(10);
    private final JTextField categoryField = new JTextField(15);
    private final JTextField noteField = new JTextField(20);
    private final JLabel errorLabel = new JLabel(" ");

    private Listener listener;
    private TransactionType currentType;
    private int editId = -1;

    /**
     * Constructs a modal transaction dialog.
     *
     * @param owner parent window
     */
    public TransactionFormDialog(Window owner) {
        super(owner, "Transaction", ModalityType.APPLICATION_MODAL);
        buildUI();
    }

    /**
     * Builds the form layout and Save/Cancel buttons.
     */
    private void buildUI() {
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        gbc.gridx = 0;
        gbc.gridy = row;
        form.add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        form.add(dateField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        form.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        form.add(amountField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        form.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        form.add(categoryField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        form.add(new JLabel("Note:"), gbc);
        gbc.gridx = 1;
        form.add(noteField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        errorLabel.setForeground(Color.RED);
        form.add(errorLabel, gbc);

        JPanel buttons = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttons.add(saveButton);
        buttons.add(cancelButton);

        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> onCancel());

        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
    }

    /**
     * Registers a listener that will be notified on save/cancel.
     *
     * @param listener listener to register (may be null to detach)
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Opens the dialog in "add" mode for the given type.
     *
     * @param type {@link TransactionType#INCOME} or {@link TransactionType#EXPENSE}
     */
    public void openForAdd(TransactionType type) {
        this.currentType = type;
        this.editId = -1;
        setTitle(type == TransactionType.INCOME ? "Add Income" : "Add Expense");
        clearFields();
        setVisible(true);
    }

    /**
     * Opens the dialog in "edit" mode with an existing transaction.
     *
     * @param tx existing transaction to edit
     */
    public void openForEdit(Transaction tx) {
        this.currentType = tx.getType();
        this.editId = tx.getId();
        setTitle("Edit Transaction");
        dateField.setText(tx.getDate().toString());
        amountField.setText(Double.toString(tx.getAmount()));
        categoryField.setText(tx.getCategory().getName());
        noteField.setText(tx.getNote());
        errorLabel.setText(" ");
        setVisible(true);
    }

    /**
     * Resets all input fields and error label.
     */
    private void clearFields() {
        dateField.setText(LocalDate.now().toString());
        amountField.setText("");
        categoryField.setText("");
        noteField.setText("");
        errorLabel.setText(" ");
    }

    /**
     * Validates the form fields and notifies the listener on success.
     * If validation fails, shows an error message in-line.
     */
    private void onSave() {
        try {
            LocalDate date = LocalDate.parse(dateField.getText().trim());
            double amount = Double.parseDouble(amountField.getText().trim());
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }
            Category category = new Category(categoryField.getText().trim());
            String note = noteField.getText().trim();

            int id = (editId < 0) ? 0 : editId; // Model will assign a real id for "add"
            Transaction tx = new Transaction(id, date, amount, currentType, category, note);

            if (listener != null) {
                listener.saveClicked(tx);
            }
            setVisible(false);
        } catch (Exception ex) {
            showValidationError(ex.getMessage());
        }
    }

    /**
     * Handles Cancel by notifying the listener (if any) and hiding the dialog.
     */
    private void onCancel() {
        if (listener != null) {
            listener.canceled();
        }
        setVisible(false);
    }

    /**
     * Displays a validation error message in the dialog.
     *
     * @param message error text to show (non-null)
     */
    public void showValidationError(String message) {
        errorLabel.setText(message);
    }
}
