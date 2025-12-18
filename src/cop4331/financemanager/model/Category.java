package cop4331.financemanager.model;

/**
 * Simple value object representing a transaction category.

 */
public class Category {

    private final String name;

    /**
     * Creates a new category.
     *
     * @param name non-null, non-empty category name
     * @throws IllegalArgumentException if {@code name} is null or blank
     */
    public Category(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name must not be empty");
        }
        this.name = name.trim();
    }

    /**
     * Returns the category name.
     *
     * @return category name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
