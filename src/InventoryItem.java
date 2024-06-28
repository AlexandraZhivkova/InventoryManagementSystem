public class InventoryItem extends AbstractItem {
    protected int availableQuantity;

    public InventoryItem(int itemID, String name, String category, double price, int availableQuantity) {
        this.itemID = itemID;
        this.name = name;
        this.category = category;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public InventoryItem(int itemID, String name, String category, double price) {
        this.itemID = itemID;
        this.name = name;
        this.category = category;
        this.price = price;
        availableQuantity = 1;
    }

    public int getID() {
        return itemID;
    }

    public void setID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int quantity) {
        availableQuantity = quantity;
    }

    public String getDetails() {
        return String.format("Item: %s, Category: %s, Price: %.2f BGN, Available quantity: %d", name, category, price, availableQuantity);
    }

    public String writeItem() {
        return String.format("%d;%s;%s;%.2f;%d\n", itemID, name, category, price, availableQuantity);
    }
}
