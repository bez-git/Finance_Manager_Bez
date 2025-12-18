package cop4331.financemanager.model;

import cop4331.financemanager.persistence.PersistenceStrategy;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Central domain/model class.
 * <p>
 * Maintains the list of transactions and notifies registered listeners
 * when data changes (Observer pattern using {@link ChangeListener}).
 * It also delegates loading and saving to a {@link PersistenceStrategy}.
 */
public class FinanceModel {

    private final List<Transaction> transactions = new ArrayList<>();
    private final List<ChangeListener> listeners = new ArrayList<>();
    private final PersistenceStrategy persistenceStrategy;

    private int nextId = 1;

    /**
     * Creates a finance model that uses the given persistence strategy.
     *
     * @param persistenceStrategy non-null strategy used to load/save model data
     * @throws IllegalArgumentException if {@code persistenceStrategy} is null
     */
    public FinanceModel(PersistenceStrategy persistenceStrategy) {
        if (persistenceStrategy == null) {
            throw new IllegalArgumentException("persistenceStrategy must not be null");
        }
        this.persistenceStrategy = persistenceStrategy;
    }

    // Observer suppor

    /**
     * Registers a listener that will be notified whenever the model changes.
     *
     * @param l listener to add; ignored if {@code null} or already added
     */
    public void addChangeListener(ChangeListener l) {
        if (l != null && !listeners.contains(l)) {
            listeners.add(l);
        }
    }

    private void fireChangeEvent() {
        ChangeEvent evt = new ChangeEvent(this);
        for (ChangeListener l : new ArrayList<>(listeners)) {
            l.stateChanged(evt);
        }
    }

    //Persistence

    /**
     * Loads all transactions via the persistence strategy and recomputes
     * the next available transaction id.
     */
    public void load() {
        List<Transaction> loaded = persistenceStrategy.load();
        transactions.clear();
        transactions.addAll(loaded);

        // re-compute nextId
        nextId = 1;
        for (Transaction t : transactions) {
            if (t.getId() >= nextId) {
                nextId = t.getId() + 1;
            }
        }
        fireChangeEvent();
    }

    /**
     * Saves all current transactions using the persistence strategy.
     */
    public void save() {
        persistenceStrategy.save(transactions);
    }

    // CRUD operations 

    /**
     * Adds a new transaction. The model assigns a new unique id.
     *
     * @param tx transaction whose id is ignored; a new Transaction with
     *           a model-assigned id is stored
     * @return stored Transaction (with assigned id)
     * @throws IllegalArgumentException if {@code tx} is null
     */
    public Transaction addTransaction(Transaction tx) {
        if (tx == null) {
            throw new IllegalArgumentException("tx must not be null");
        }
        Transaction stored = new Transaction(
                nextId++,
                tx.getDate(),
                tx.getAmount(),
                tx.getType(),
                tx.getCategory(),
                tx.getNote()
        );
        transactions.add(stored);
        fireChangeEvent();
        return stored;
    }

    /**
     * Updates an existing transaction.
     *
     * @param id      id of the transaction to update
     * @param newData new transaction data (id is ignored)
     * @throws IllegalArgumentException if id does not exist
     */
    public void editTransaction(int id, Transaction newData) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId() == id) {
                Transaction updated = newData.withUpdatedData(
                        newData.getDate(),
                        newData.getAmount(),
                        newData.getType(),
                        newData.getCategory(),
                        newData.getNote()
                );
                transactions.set(i, updated);
                fireChangeEvent();
                return;
            }
        }
        throw new IllegalArgumentException("No transaction with id " + id);
    }

    /**
     * Deletes the transaction with the given id, if present.
     *
     * @param id id of the transaction to delete
     */
    public void deleteTransaction(int id) {
        transactions.removeIf(t -> t.getId() == id);
        fireChangeEvent();
    }

    /**
     * Returns an unmodifiable view of all transactions.
     *
     * @return list of transactions
     */
    public List<Transaction> getAllTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    /**
     * Finds a transaction by id.
     *
     * @param id transaction id
     * @return matching transaction or {@code null} if not found
     */
    public Transaction getTransactionById(int id) {
        for (Transaction t : transactions) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    /**
     * Computes a monthly summary for the given year-month.
     *
     * @param month target month; must not be {@code null}
     * @return summary DTO with totals and balance
     */
    public SummaryDTO getMonthlySummary(YearMonth month) {
        double income = 0;
        double expense = 0;

        for (Transaction t : transactions) {
            if (YearMonth.from(t.getDate()).equals(month)) {
                if (t.getType() == TransactionType.INCOME) {
                    income += t.getAmount();
                } else {
                    expense += t.getAmount();
                }
            }
        }

        return new SummaryDTO(month.getYear(), month.getMonthValue(), income, expense);
    }

    /**
     * Returns the current overall balance (income - expenses).
     *
     * @return current balance
     */
    public double getCurrentBalance() {
        double income = 0;
        double expense = 0;
        for (Transaction t : transactions) {
            if (t.getType() == TransactionType.INCOME) {
                income += t.getAmount();
            } else {
                expense += t.getAmount();
            }
        }
        return income - expense;
    }
}
