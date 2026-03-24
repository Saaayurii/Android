package com.example.shoppinglist;

public class Purchase {
    private String name;
    private double price;
    private int quantity;

    public Purchase(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String toStorageString() {
        return name + "|" + price + "|" + quantity;
    }

    public static Purchase fromStorageString(String s) {
        String[] parts = s.split("\\|");
        if (parts.length != 3) return null;
        try {
            return new Purchase(parts[0], Double.parseDouble(parts[1]), Integer.parseInt(parts[2]));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
