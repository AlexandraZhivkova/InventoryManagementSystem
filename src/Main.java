import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String[] categories = {"Grocery", "Electronics", "Fragile"};
    static String[] payments = {"Cash", "Debit Card", "PayPal"};

    public static void showMenu() {
        System.out.println("--------------------------------------------\nMENU\n--------------------------------------------");
        System.out.println("1. Add new item to inventory");
        System.out.println("2. Remove item from inventory");
        System.out.println("3. Display all inventory items");
        System.out.println("4. Display inventory items by category");
        System.out.println("5. Place new order");
        System.out.println("6. Display all orders");
        System.out.println("0. Save and exit");
        System.out.println("--------------------------------------------");
    }

    public static void showCategories() {
        System.out.println("--------------------------------------------\nCATEGORIES\n--------------------------------------------");
        for (int i = 0; i < categories.length; i++)
            System.out.printf("%d. %s\n", i + 1, categories[i]);
        System.out.println("--------------------------------------------");
    }

    public static String chooseCategory() {
        String category = null;
        showCategories();
        do {
            System.out.print("Enter item category: ");
            int categoryID = Integer.parseInt(scanner.nextLine()) - 1;
            if (categoryID >= 0 && categoryID < categories.length)
                category = categories[categoryID];
            else
                System.out.println("Please, choose existing category.");
        } while (category == null);
        return category;
    }

    public static void saveInventoryItemsToFile(ArrayList<InventoryItem> items) {
        try (FileOutputStream fileOut = new FileOutputStream("inventory.ser");
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
            for (InventoryItem item : items)
                objOut.writeObject(item);
        } catch (IOException e) {
            System.out.println("Cannot write to file.");
        }
    }

    public static ArrayList<InventoryItem> readInventoryItemsFromFile() {
        ArrayList<InventoryItem> items;
        items = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream("inventory.ser");
             ObjectInputStream objIn = new ObjectInputStream(fileIn)) {
            while (true) {
                InventoryItem item = (InventoryItem) objIn.readObject();
                if (item == null)
                    break;
                else
                    items.add(item);
            }
        } catch (IOException e) {
            System.out.println("Cannot read from file");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    public static void showPaymentTypes() {
        System.out.println("--------------------------------------------\nPAYMENT TYPES\n--------------------------------------------");
        for (int i = 0; i < payments.length; i++)
            System.out.printf("%d. %s\n", i + 1, payments[i]);
        System.out.println("--------------------------------------------");
    }

    public static String choosePaymentType() {
        String payment = null;
        showPaymentTypes();
        do {
            System.out.print("Enter item category: ");
            int paymentID = Integer.parseInt(scanner.nextLine()) - 1;
            if (paymentID >= 0 && paymentID < payments.length)
                payment = payments[paymentID];
            else
                System.out.println("Please, choose existing category.");
        } while (payment == null);
        return payment;
    }

    public static void showOrderMenu() {
        System.out.println("--------------------------------------------\nORDER MENU\n--------------------------------------------");
        System.out.println("1. Add item");
        System.out.println("2. Remove item");
        System.out.println("3. Finish order");
        System.out.println("0. Back to Menu");
        System.out.println("--------------------------------------------");
    }

    public static void showItems(ArrayList<InventoryItem> items) {
        for (InventoryItem item : items)
            System.out.printf("%d. %s\n", item.getID(), item.getDetails());
    }

    public static InventoryItem chooseItem(ArrayList<InventoryItem> items) {
        int id = 0, quantity = 0;
        String name = null, category = null;
        double price = 0, weight = 0;
        do {
            try {
                System.out.print("Choose item: ");
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please, choose valid option.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please, enter existing ID.");
            }
        } while (id <= 0);
        for (InventoryItem item : items)
            if (id == item.itemID) {
                String[] itemDetails = item.writeItem().split(";");
                id = item.getID();
                name = itemDetails[1];
                category = item.getCategory();
                price = item.getPrice();
                quantity = item.getAvailableQuantity();
                if (category.equals("Fragile"))
                    weight = Double.parseDouble(itemDetails[5]);
            }
        if (category.equals("Fragile"))
            return new FragileItem(id, name, category, price, quantity, weight);
        else
            return new InventoryItem(id, name, category, price, quantity);
    }

    public static void showOrders(ArrayList<Order> orders) {
        for (Order order : orders)
            order.viewOrderDetails();
    }

    public static void main(String[] args) {
        ArrayList<InventoryItem> items = readInventoryItemsFromFile();
        ArrayList<Order> orders = new ArrayList<>();
        int id;
        if (items.isEmpty())
            id = 1;
        else
            id = items.getLast().itemID + 1;
        boolean exit = false;
        while (!exit) {
            try {
                showMenu();
                System.out.print("Choose option: ");
                int command = Integer.parseInt(scanner.nextLine());
                switch (command) {
                    case 0:
                        saveInventoryItemsToFile(items);
                        exit = true;
                        break;
                    case 1:
                        System.out.println("--------------------------------------------");
                        System.out.println("1. Set available availableQuantity");
                        System.out.println("2. Default available availableQuantity");
                        System.out.println("--------------------------------------------");
                        int quantityOption = 0;
                        int availableQuantity = 0;
                        do {
                            System.out.print("Choose option: ");
                            try {
                                quantityOption = Integer.parseInt(scanner.nextLine());
                                switch (quantityOption) {
                                    case 1:
                                        do {
                                            System.out.print("Enter available quantity: ");
                                            try {
                                                availableQuantity = Integer.parseInt(scanner.nextLine());
                                            } catch (NumberFormatException e) {
                                                System.out.println("Please, enter valid value.");
                                            }
                                        } while (availableQuantity <= 0);
                                        break;
                                    case 2:
                                        availableQuantity = 1;
                                        break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Please, enter valid value.");
                            }
                        } while (quantityOption > 2 || quantityOption < 1);
                        String category = chooseCategory();
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        double price = 0;
                        do {
                            System.out.print("Enter price: ");
                            try {
                                price = Double.parseDouble(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Please, enter valid value.");
                            }
                        } while (price <= 0);
                        switch (category) {
                            case "Grocery":
                                InventoryItem groceryItem = new GroceryItem(id++, name, "Grocery", price, availableQuantity);
                                items.add(groceryItem);
                                break;
                            case "Electronics":
                                items.add(new ElectronicsItem(id++, name, "Electronics", price, availableQuantity));
                                break;
                            case "Fragile":
                                double weight = 0;
                                do {
                                    System.out.print("Enter weight in KG: ");
                                    try {
                                        weight = Double.parseDouble(scanner.nextLine());
                                    } catch (NumberFormatException e) {
                                        System.out.println("Please, enter valid value.");
                                    }
                                } while (weight <= 0);
                                items.add(new FragileItem(id++, name, "Fragile", price, availableQuantity, weight));
                                break;
                        }
                        break;
                    case 2:
                        System.out.print("Enter ID: ");
                        int removeID = Integer.parseInt(scanner.nextLine());
                        boolean found = false;
                        for (InventoryItem item : items)
                            if (item.getID() == removeID) {
                                items.remove(item);
                                found = true;
                                System.out.println("Item has been removed.");
                                break;
                            }
                        if (!found)
                            System.out.println("Item with this ID doesn't exist.");
                        break;
                    case 3:
                        showItems(items);
                        break;
                    case 4:
                        String searchCategory = chooseCategory();
                        System.out.println();
                        for (InventoryItem item : items)
                            if (item.category.equals(searchCategory))
                                System.out.println(item.getDetails());
                        break;
                    case 5:
                        String payment = choosePaymentType();
                        Order order = new Order(new Payment(payment));
                        boolean close = false;
                        while (!close) {
                            showOrderMenu();
                            try {
                                System.out.print("Choose option: ");
                                int option = Integer.parseInt(scanner.nextLine());
                                switch (option) {
                                    case 0:
                                        close = true;
                                        break;
                                    case 1:
                                        showItems(items);
                                        InventoryItem item = chooseItem(items);
                                        int quantity = 0;
                                        do {
                                            System.out.print("Enter quantity: ");
                                            try {
                                                quantity = Integer.parseInt(scanner.nextLine());
                                            } catch (NumberFormatException e) {
                                                System.out.println("Please, enter valid value.");
                                            }
                                        } while (quantity <= 0);
                                        order.addItem(item, quantity);
                                        break;
                                    case 2:
                                        if (!order.items.isEmpty()) {
                                            for (Order orderDetails : orders)
                                                for (var orderItem : orderDetails.items.entrySet())
                                                    System.out.println(orderItem.getKey().getDetails());
                                            int searchID = 0;
                                            do {
                                                try {
                                                    System.out.println("Enter ID: ");
                                                    searchID = Integer.parseInt(scanner.nextLine());
                                                } catch (NumberFormatException e) {
                                                    System.out.println("Please choose valid option.");
                                                }
                                            } while (searchID <= 0);
                                            for (var orderItem : order.items.entrySet())
                                                if (orderItem.getKey().getID() == searchID) {
                                                    order.removeItem(orderItem.getKey());
                                                    break;
                                                }
                                        } else
                                            System.out.println("The cart is empty.");
                                        break;
                                    case 3:
                                        orders.add(order);
                                        order.pay();
                                        close = true;
                                        break;

                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Please choose valid option.");
                            }
                        }
                        break;
                    case 6:
                        showOrders(orders);
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please choose valid option.");
            }
        }
    }
}