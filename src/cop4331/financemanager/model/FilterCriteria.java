package cop4331.financemanager.model;

import java.time.YearMonth;

/**
 * Simple filter object used to represent selection criteria
 * for operations like monthly summaries.
 * <p>
 * Currently only stores a {@link YearMonth}, but can be extended
 * later with more criteria (category, type, etc.).
 */
public class FilterCriteria {

    private final YearMonth month;

    /**
     * Creates a new filter for a specific month.
     *
     * @param month target year-month; may be {@code null} to mean "no filter"
     */
    public FilterCriteria(YearMonth month) {
        this.month = month;
    }

    /**
     * Returns the month associated with this filter.
     *
     * @return month, or {@code null} if not specified
     */
    public YearMonth getMonth() {
        return month;
    }
}
