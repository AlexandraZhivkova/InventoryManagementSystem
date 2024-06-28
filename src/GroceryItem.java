public class GroceryItem extends InventoryItem {
    public GroceryItem(int itemID, String name, String category, double price, int quantity) {
        super(itemID, name, category, price, quantity);
    }

    @Override
    public boolean isPerishable() {
        return true;
    }

    @Override
    public void handleExpiredItem() {
        this.availableQuantity--;
        System.out.println("This item has expired and has been removed from the inventory.");
    }

    @Override
    public double calculateValue(int quantity) {
        return super.calculateValue(quantity) * 1.2;
    }
}
