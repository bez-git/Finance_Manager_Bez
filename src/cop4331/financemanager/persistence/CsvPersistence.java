package cop4331.financemanager.persistence;

import cop4331.financemanager.model.Category;
import cop4331.financemanager.model.Transaction;
import cop4331.financemanager.model.TransactionType;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple CSV-based persistence implementation.
 * <p>
 * File format: {@code id;date(YYYY-MM-DD);amount;type;category;note}
 */
public class CsvPersistence implements PersistenceStrategy {

    private final File file;

    /**
     * Creates a new CSV persistence strategy for the given file.
     *
     * @param file target CSV file (may or may not exist yet)
     */
    public CsvPersistence(File file) {
        this.file = file;
    }

    /**
     * Loads all transactions from the CSV file.
     *
     * @return list of transactions; empty list if file does not exist
     */
    @Override
    public List<Transaction> load() {
        List<Transaction> result = new ArrayList<>();
        if (!file.exists()) {
            return result;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(";", -1);
                int id = Integer.parseInt(parts[0]);
                LocalDate date = LocalDate.parse(parts[1]);
                double amount = Double.parseDouble(parts[2]);
                TransactionType type = TransactionType.valueOf(parts[3]);
                Category category = new Category(parts[4]);
                String note = parts.length > 5 ? parts[5] : "";
                result.add(new Transaction(id, date, amount, type, category, note));
            }
        } catch (IOException | RuntimeException ex) {
            ex.printStackTrace(); // for project you can show dialog instead
        }
        return result;
    }

    /**
     * Saves all transactions to the CSV file.
     *
     * @param data list of transactions to persist
     */
    @Override
    public void save(List<Transaction> data) {
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            for (Transaction t : data) {
                out.printf("%d;%s;%f;%s;%s;%s%n",
                        t.getId(),
                        t.getDate(),
                        t.getAmount(),
                        t.getType().name(),
                        t.getCategory().getName(),
                        t.getNote().replace(";", ","));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
