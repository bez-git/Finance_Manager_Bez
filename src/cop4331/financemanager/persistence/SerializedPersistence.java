package cop4331.financemanager.persistence;

import cop4331.financemanager.model.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistence strategy using Java object serialization.
 */
public class SerializedPersistence implements PersistenceStrategy {

    private final File file;

    /**
     * Creates a new serialized persistence strategy.
     *
     * @param file target file used for serialized data
     */
    public SerializedPersistence(File file) {
        this.file = file;
    }

    /**
     * Loads all serialized transactions from disk.
     *
     * @return list of transactions; empty list if file does not exist
     */
    @Override
    public List<Transaction> load() {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            @SuppressWarnings("unchecked")
            List<Transaction> list = (List<Transaction>) in.readObject();
            return list;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Saves all transactions to disk using Java serialization.
     *
     * @param data list of transactions to persist
     */
    @Override
    public void save(List<Transaction> data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(new ArrayList<>(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
