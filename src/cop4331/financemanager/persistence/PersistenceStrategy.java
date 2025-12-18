package cop4331.financemanager.persistence;

import cop4331.financemanager.model.Transaction;

import java.util.List;

/**
 * Strategy interface for loading/saving transactions (Strategy pattern).
 */
public interface PersistenceStrategy {

    /**
     * Loads all transactions from the underlying storage.
     *
     * @return list of transactions (never {@code null})
     */
    List<Transaction> load();

    /**
     * Saves all transactions to the underlying storage.
     *
     * @param data list of transactions to persist
     */
    void save(List<Transaction> data);
}
