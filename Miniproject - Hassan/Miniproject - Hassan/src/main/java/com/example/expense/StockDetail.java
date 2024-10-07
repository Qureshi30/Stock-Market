package com.example.expense;

// Stock class definition
class StockDetail {
    private String name;
    private int quantity;
    private double price;
    private String tradeType;
    private String entryDate;
    private String exitDate;

    public StockDetail(String name, int quantity, double price, String tradeType, String entryDate, String exitDate) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.tradeType = tradeType;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getTradeType() {
        return tradeType;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getExitDate() {
        return exitDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public void setExitDate(String exitDate) {
        this.exitDate = exitDate;
    }
}
