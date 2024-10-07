package com.example.expense;

import javafx.beans.property.*;

public class StockItem {
    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty quantity;
    private final StringProperty entryDate;
    private final StringProperty exitDate; // Renamed from expiryDate to exitDate
    private final StringProperty tradeType; // Fixed naming issue
    private final IntegerProperty price;
    private final IntegerProperty sellPrice; // Replaced total with sellPrice

    // Constructor
    public StockItem(int id, String name, int quantity, int price, int sellPrice, String tradeType, String entryDate, String exitDate) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.entryDate = new SimpleStringProperty(entryDate);
        this.exitDate = new SimpleStringProperty(exitDate);
        this.tradeType = new SimpleStringProperty(tradeType); // Use SimpleStringProperty for tradeType
        this.price = new SimpleIntegerProperty(price);
        this.sellPrice = new SimpleIntegerProperty(sellPrice);
    }


    // Getters
    public int getStockId() { return id.get(); }
    public String getName() { return name.get(); }
    public int getQuantity() { return quantity.get(); }
    public String getEntryDate() { return entryDate.get(); }
    public String getExitDate() { return exitDate.get(); } // Updated getter
    public String getTradeType() { return tradeType.get(); } // Added getter for tradeType
    public int getPrice() { return (int) price.get(); }
    public int getSellPrice() { return (int) sellPrice.get(); } // Updated getter for sellPrice

    // Setters
    public void setStockId(int id) { this.id.set(id); }
    public void setName(String name) { this.name.set(name); }
    public void setQuantity(int quantity) { this.quantity.set(quantity); }
    public void setEntryDate(String entryDate) { this.entryDate.set(entryDate); }
    public void setExitDate(String exitDate) { this.exitDate.set(exitDate); } // Updated setter
    public void setTradeType(String tradeType) { this.tradeType.set(tradeType); } // Added setter for tradeType
    public void setPrice(int price) { this.price.set(price); }
    public void setSellPrice(int sellPrice) { this.sellPrice.set(sellPrice); } // Updated setter for sellPrice

    // Export to CSV
//    public String toCSV() {
//        return String.join(",", getId(), getName(), String.valueOf(getQuantity()), getEntryDate(), getExitDate(),
//                String.valueOf(getPrice()), String.valueOf(getSellPrice())) + "\n"; // Updated toCSV method
//    }
}
