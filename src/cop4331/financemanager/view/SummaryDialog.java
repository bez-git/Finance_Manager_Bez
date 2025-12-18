package cop4331.financemanager.view;

import cop4331.financemanager.model.SummaryDTO;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog that displays a monthly summary.
 */
public class SummaryDialog extends JDialog {

    private final JLabel monthLabel = new JLabel();
    private final JLabel incomeLabel = new JLabel();
    private final JLabel expenseLabel = new JLabel();
    private final JLabel balanceLabel = new JLabel();

    /**
     * Creates a modal dialog for showing a {@link SummaryDTO}.
     *
     * @param owner parent window
     */
    public SummaryDialog(Window owner) {
        super(owner, "Monthly Summary", ModalityType.APPLICATION_MODAL);
        buildUI();
    }

    /**
     * Builds the label layout and close button.
     */
    private void buildUI() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 4, 4));
        panel.add(monthLabel);
        panel.add(incomeLabel);
        panel.add(expenseLabel);
        panel.add(balanceLabel);

        JButton close = new JButton("Close");
        close.addActionListener(e -> setVisible(false));

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(close, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getOwner());
    }

    /**
     * Populates the labels from the given DTO and shows the dialog.
     *
     * @param dto monthly summary to display
     */
    public void showSummary(SummaryDTO dto) {
        monthLabel.setText("Month: " + dto.getMonth() + "/" + dto.getYear());
        incomeLabel.setText("Total income: " + dto.getTotalIncome());
        expenseLabel.setText("Total expense: " + dto.getTotalExpense());
        balanceLabel.setText("Balance: " + dto.getBalance());
        setVisible(true);
    }
}
