package com.example.expense;

public class Stock {
    private String name;
    private int quantity;
    private int price;
    private int sellPrice;  // Ensure this field exists
    private String tradeType;
    private String entryDate;
    private String exitDate;

    public Stock(String name, int quantity, int price, String tradeType, String entryDate, String exitDate, int sellPrice) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        
        this.tradeType = tradeType;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.sellPrice = sellPrice; // Make sure to assign this value
    }

    // Getters and Setters
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public int getPrice() { return price; }
    public int getSellPrice() { return sellPrice; }
    public String getTradeType() { return tradeType; }
    public String getEntryDate() { return entryDate; }
    public String getExitDate() { return exitDate; }

    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(int price) { this.price = price; }
    public void setSellPrice(int sellPrice) { this.sellPrice = sellPrice; } // Add a setter for sellPrice
    public void setTradeType(String tradeType) { this.tradeType = tradeType; }
    public void setEntryDate(String entryDate) { this.entryDate = entryDate; }
    public void setExitDate(String exitDate) { this.exitDate = exitDate; }
}
