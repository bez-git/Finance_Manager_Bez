package cop4331.financemanager;

/**
 * Entry point for the Finance Manager application.
 *
 * <p>Creates the persistence layer, model, and main Swing frame, then
 * starts the UI on the Event Dispatch Thread.</p>
 */

import cop4331.financemanager.model.FinanceModel;
import cop4331.financemanager.persistence.CsvPersistence;
import cop4331.financemanager.view.MainFrame;

import javax.swing.*;
import java.io.File;

/**
 * Starts the Finance Manager application.
 */
public class AppMain {

    /**
     * Main method that boots the Finance Manager.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            File dataFile = new File("finance-data.csv");
            CsvPersistence persistence = new CsvPersistence(dataFile);
            FinanceModel model = new FinanceModel(persistence);
            model.load();

            MainFrame frame = new MainFrame(model);
            frame.setVisible(true);
        });
    }
}
