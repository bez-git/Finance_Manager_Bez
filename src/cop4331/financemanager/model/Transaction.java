package cop4331.financemanager.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Immutable domain object representing a single financial transaction.
 * <p>
 * A transaction has:
 * <ul>
 *     <li>a unique id assigned by {@link FinanceModel}</li>
 *     <li>a date</li>
 *     <li>an amount</li>
 *     <li>a {@link TransactionType} (INCOME or EXPENSE)</li>
 *     <li>a {@link Category}</li>
 *     <li>an optional note</li>
 * </ul>
 */
public class Transaction implements Serializable {

    private final int id;
    private final LocalDate date;
    private final double amount;
    private final TransactionType type;
    private final Category category;
    private final String note;

    /**
     * Creates an immutable transaction.
     *
     * @param id       unique identifier assigned by {@link FinanceModel}
     * @param date     non-null transaction date
     * @param amount   strictly positive amount
     * @param type     INCOME or EXPENSE; must not be {@code null}
     * @param category non-null category
     * @param note     optional note (can be empty, but not {@code null})
     * @throws IllegalArgumentException if any precondition is violated
     */
    public Transaction(int id,
                       LocalDate date,
                       double amount,
                       TransactionType type,
                       Category category,
                       String note) {

        if (date == null) {
            throw new IllegalArgumentException("date must not be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (type == null) {
            throw new IllegalArgumentException("type must not be null");
        }
        if (category == null) {
            throw new IllegalArgumentException("category must not be null");
        }
        if (note == null) {
            note = "";
        }

        this.id = id;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public String getNote() {
        return note;
    }

    /**
     * Returns a new {@code Transaction} instance with the same id but updated fields.
     * This preserves immutability while allowing the model to "edit" transactions.
     *
     * @param date     new date
     * @param amount   new amount
     * @param type     new type
     * @param category new category
     * @param note     new note
     * @return new Transaction instance with updated data
     */
    public Transaction withUpdatedData(LocalDate date,
                                       double amount,
                                       TransactionType type,
                                       Category category,
                                       String note) {
        return new Transaction(id, date, amount, type, category, note);
    }
}
