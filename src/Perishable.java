public interface Perishable {
    boolean isPerishable();

    void handleExpiredItem();
}
