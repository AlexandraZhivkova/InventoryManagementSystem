import java.io.Serializable;
import java.util.ArrayList;

public abstract class AbstractItem implements Breakable,Categorizable,Item,Perishable,Sellable, Serializable {

    protected int itemID;
    protected String name;
    protected String category;
    protected double price;

    @Override
    public boolean isBreakable() {
        return false;
    }

    @Override
    public void handleBreakage() {
        System.out.println("We are sorry. Your item will be repaired or you will get a refund.");
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getDetails() {
        return String.format("%s;%s;%.2f%n", name, category, price);
    }

    @Override
    public double calculateValue(int quantity) {
        return price * quantity;
    }

    @Override
    public void viewDescription() {
        ArrayList<String> description = new ArrayList<>();
        if (isBreakable())
            description.add("Breakable");
        if (isPerishable())
            description.add("Perishable");
        System.out.println(String.join(", ", description));
    }

    @Override
    public boolean isPerishable() {
        return false;
    }

    @Override
    public void handleExpiredItem() {
        System.out.println("This item has expired.");
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }
}
