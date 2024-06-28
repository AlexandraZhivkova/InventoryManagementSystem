public class ElectronicsItem extends InventoryItem {
    public ElectronicsItem(int itemID, String name, String category, double price, int quantity) {
        super(itemID, name, category, price, quantity);
    }

    @Override
    public boolean isBreakable() {
        return true;
    }

    @Override
    public double calculateValue(int quantity) {
        if (quantity > 4)
            return super.calculateValue(quantity) * 0.8;
        else
            return super.calculateValue(quantity);
    }
}
