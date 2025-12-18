package cop4331.financemanager.model;

/**
 * Data transfer object (DTO) used by the ViewSummary use case
 * to expose monthly totals without leaking internal model details.
 */
public class SummaryDTO {

    private final int year;
    private final int month;
    private final double totalIncome;
    private final double totalExpense;

    /**
     * Creates a new immutable summary DTO.
     *
     * @param year          calendar year
     * @param month         month number (1–12)
     * @param totalIncome   sum of all income transactions for the month
     * @param totalExpense  sum of all expense transactions for the month
     */
    public SummaryDTO(int year, int month, double totalIncome, double totalExpense) {
        this.year = year;
        this.month = month;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    /**
     * Returns the net balance (income - expenses) for this month.
     *
     * @return monthly balance
     */
    public double getBalance() {
        return totalIncome - totalExpense;
    }
}
