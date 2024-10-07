package com.example.expense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class AccountController {

    private static final String URL = "jdbc:mysql://localhost:3306/trademarket";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Class-level ObservableList
    private ObservableList<Stock> stockList = FXCollections.observableArrayList();

    @FXML
    private TextField stockNameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<String> tradeTypeComboBox;
    @FXML
    private DatePicker entryDatePicker;
    @FXML
    private DatePicker exitDatePicker;
    @FXML
    private TableView<Stock> stockTableView;

    @FXML
    private TableColumn<Stock, String> nameColumn;
    @FXML
    private TableColumn<Stock, Integer> quantityColumn;
    @FXML
    private TableColumn<Stock, Double> priceColumn;
    @FXML
    private TableColumn<Stock, String> tradeTypeColumn;
    @FXML
    private TableColumn<Stock, String> entryDateColumn;
    @FXML
    private TableColumn<Stock, String> exitDateColumn;
    @FXML
    private TableColumn<Stock, Integer> sellPriceColumn; // Add this column to the TableView

    @FXML
    public void initialize() {
        // Initialize TableView columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        tradeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("tradeType"));
        entryDateColumn.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
        exitDateColumn.setCellValueFactory(new PropertyValueFactory<>("exitDate"));
        sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice")); // Set the cell value factory

        // Initialize the trade type options
        tradeTypeComboBox.getItems().addAll("Long Term", "Short Term");

        // Set the ObservableList as the items of the TableView
        stockTableView.setItems(stockList);

        // Load existing stock data into the table
        setupTableView();
    }

    private void setupTableView() {
        String query = "SELECT name, quantity, price, sellPrice, trade, entry, exit_ FROM stock;"; // Make sure to select sellPrice

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            stockList.clear();  // Clear the list before loading new data

            while (rs.next()) {
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                String tradeType = rs.getString("trade");
                String entryDate = rs.getString("entry");
                String exitDate = rs.getString("exit_");
                int sellPrice = rs.getInt("sellPrice"); // Fetch sellPrice

                Stock stock = new Stock(name, quantity, price, tradeType, entryDate, exitDate, sellPrice);
                stockList.add(stock);  // Add the new stock to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private TextField sellPriceField; // Add this field to capture sell price

    @FXML
    private void addStock(ActionEvent event) {
        String name = stockNameField.getText();
        int price = Integer.parseInt(priceField.getText());
        int sellPrice = Integer.parseInt(sellPriceField.getText()); // Get sell price
        int quantity = Integer.parseInt(quantityField.getText());
        String tradeType = tradeTypeComboBox.getValue();
        String entryDate = entryDatePicker.getValue() != null ? entryDatePicker.getValue().toString() : "";
        String exitDate = exitDatePicker.getValue() != null ? exitDatePicker.getValue().toString() : "";

        String sql = "INSERT INTO stock(name, quantity, price, sellPrice, trade, entry, exit_) VALUES (?, ?, ?, ?, ?, ?, ?);"; // Updated to include sellPrice
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, quantity);
            pstmt.setInt(3, price);
            pstmt.setInt(4, sellPrice); // Set sellPrice
            pstmt.setString(5, tradeType);
            pstmt.setString(6, entryDate);
            pstmt.setString(7, exitDate);
            pstmt.executeUpdate();

            // Add the new stock to the ObservableList and TableView
            Stock newStock = new Stock(name, quantity, price, tradeType, entryDate, exitDate, sellPrice); // Updated to include sellPrice
            stockList.add(newStock);  // Add to the ObservableList

            System.out.println("Stock record added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Clear input fields
        clearInputFields();
    }


    private void clearInputFields() {
        stockNameField.clear();
        priceField.clear();
        sellPriceField.clear();
        quantityField.clear();
        tradeTypeComboBox.setValue(null);
        entryDatePicker.setValue(null);
        exitDatePicker.setValue(null);
    }
}

