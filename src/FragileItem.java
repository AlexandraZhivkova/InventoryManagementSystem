public class FragileItem extends InventoryItem {
    double weight;

    public FragileItem(int itemID, String name, String category, double price, int quantity, double weight) {
        super(itemID, name, category, price, quantity);
        this.weight = weight;
    }

    public void setWeight(double weight) {
        if (weight > 0) {
            this.weight = weight;
        } else
            System.out.println("Weight has to be a positive value");
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean isBreakable() {
        return true;
    }

    @Override
    public double calculateValue(int quantity) {
        if (weight <= 0.5)
            return super.calculateValue(quantity);
        else if (weight <= 5)
            return super.calculateValue(quantity) + (weight - 1.5) * 0.6;
        else
            return super.calculateValue(quantity) + (weight - 5) * 1.2;
    }

    @Override
    public String getDetails() {
        return String.format("Item: %s, Category: %s, Price: %.2f BGN, Weight: %.3f, Available quantity: %d", name, category, price, weight, availableQuantity);
    }
}
