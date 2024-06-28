import java.util.HashMap;

public class Order {
    private static int id = 1;
    int orderID;
    HashMap<InventoryItem, Integer> items = new HashMap<>();
    Payment payment;
    double totalCost = 0;

    public Order(Payment payment) {
        this.orderID = id++;
        this.payment = payment;
    }

    public void addItem(InventoryItem item, int quantity) {
        if (quantity > 0) {
            if (items.containsKey(item))
                System.out.println("You have already added this item.");
            else {
                if (item.availableQuantity < 1)
                    System.out.printf(item.name + " is not available for sale.");
                else if (item.availableQuantity >= quantity) {
                    items.put(item, quantity);
                    item.setAvailableQuantity(item.getAvailableQuantity()-quantity);
                    System.out.println(item.name + " has been added to your order.");
                    totalCost += item.calculateValue(quantity);
                } else
                    System.out.println("Not enough available quantity in inventory.%nCurrently available: " + item.availableQuantity);
            }
        } else
            System.out.println("Please, enter valid quantity");
    }

    public void removeItem(InventoryItem item) {
        if (items.containsKey(item)) {
            int quantity = items.get(item);
            items.remove(item);
            item.setAvailableQuantity(item.getAvailableQuantity()+quantity);
            totalCost -= item.calculateValue(quantity);
            System.out.println(item.name + " has been removed");
        } else {
            System.out.println("Your order does not contain this item.");
        }
    }

    public void viewItems() {
        for (var entry : items.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("%s, Quantity: %d, Sum: %.2f BGN\n", item.getDetails(), quantity, item.calculateValue(quantity));
        }
    }

    public void viewOrderDetails() {
        System.out.println();
        System.out.println("Order "+orderID);
        viewItems();
        System.out.printf("Payment method: %s, Totsl cost: %.2f BGN\n", payment.toString(), totalCost);
    }

    public void pay() {
        if (payment.isProcessed())
            System.out.println("This order is already payed for.");
        else {
            System.out.printf("Total: %.2f BGN\n", totalCost);
            payment.processPayment();
            System.out.println("Successful payment.");
        }
    }
}