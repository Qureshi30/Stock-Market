package com.example.expense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

import com.example.expense.StockItem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;

public class StockController {
    private static final String URL = "jdbc:mysql://localhost:3306/trademarket";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @FXML
    private TableView<StockItem> updatetable;
    @FXML
    private TableColumn<StockItem, String> stockId;
    @FXML
    private TableColumn<StockItem, String> stockItemName; // Renamed for consistency
    @FXML
    private TableColumn<StockItem, Integer> stockItemQuantity; // Renamed for consistency
    @FXML
    private TableColumn<StockItem, String> stockItemEntryDate; // Renamed for consistency
    @FXML
    private TableColumn<StockItem, String> stockItemExitDate; // Renamed for consistency
    @FXML
    private TableColumn<StockItem, Double> stockItemPrice; // Renamed for consistency
    @FXML
    private TableColumn<StockItem, Double> stockItemSellPrice; // Renamed for consistency

    @FXML
    private TableColumn<StockItem, String> tradeTypeColumn; // Renamed for consistency

    @FXML
    private TextField tf_search;
    @FXML
    private DatePicker datePicker;

    private final ObservableList<StockItem> stockItemList = FXCollections.observableArrayList(); // Renamed for consistency

    @FXML
    public void initialize() {
        // Set up table columns
       stockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
       stockItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
       stockItemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
       stockItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
       tradeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("tradeType"));
       stockItemEntryDate.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
       stockItemExitDate.setCellValueFactory(new PropertyValueFactory<>("exitDate"));
       stockItemSellPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
       updatetable.setItems(stockItemList);

        // Load initial stock data from the database
        loadStockData();
    }

   @FXML
   private void handleSearch() {
       ObservableList<StockItem> filteredList = FXCollections.observableArrayList();
       String searchKeyword = tf_search.getText().toLowerCase();

       for (StockItem item : stockItemList) {
           String stringId = String.valueOf(item.getStockId());
           boolean matchesSearch = stringId.contains(searchKeyword) ||
                   item.getName().toLowerCase().contains(searchKeyword);
 
           if (matchesSearch) {
               filteredList.add(item);
           }
       }

       updatetable.setItems(filteredList);

       if (filteredList.isEmpty()) {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setContentText("Java  Hai");
           alert.showAndWait();
       }
   }

   @FXML

   private void handleUpdate() {
       StockItem selectedStockItem = updatetable.getSelectionModel().getSelectedItem();
       if (selectedStockItem != null) {
           Dialog<StockItem> dialog = new Dialog<>();
           dialog.setTitle("Update Stock Item");
           dialog.setHeaderText("Update Stock Item Details");

           TextField idField = new TextField(String.valueOf(selectedStockItem.getStockId()));
           TextField nameField = new TextField(selectedStockItem.getName());
           TextField quantityField = new TextField(String.valueOf(selectedStockItem.getQuantity()));
           TextField priceField = new TextField(String.valueOf(selectedStockItem.getPrice()));
           TextField sellPriceField = new TextField(String.valueOf(selectedStockItem.getSellPrice()));

           ComboBox<String> tradeTypeComboBox = new ComboBox<>();
           tradeTypeComboBox.getItems().addAll("Long Term", "Short Term");
           tradeTypeComboBox.setValue(selectedStockItem.getTradeType());

           // DatePickers for Entry and Exit Dates
           DatePicker entryDatePicker = new DatePicker();
           DatePicker exitDatePicker = new DatePicker();

           // hassan chakka teri chaddi me kala chuva gaya phati huvi chaddi mat pehna kar!!!

           // Set values to DatePickers from StockItem
           if (selectedStockItem.getEntryDate() != null && !selectedStockItem.getEntryDate().isEmpty()) {
               entryDatePicker.setValue(LocalDate.parse(selectedStockItem.getEntryDate()));
           }

           if (selectedStockItem.getExitDate() != null && !selectedStockItem.getExitDate().isEmpty()) {
               exitDatePicker.setValue(LocalDate.parse(selectedStockItem.getExitDate()));
           }
// Create GridPane
           GridPane grid = new GridPane();
           grid.setPadding(new Insets(20)); // Padding around the grid
           grid.setHgap(15); // Horizontal gap between columns
           grid.setVgap(10); // Vertical gap between rows
           grid.setAlignment(Pos.CENTER); // Center the grid

// Apply CSS styling for better appearance
           String labelStyle = "-fx-font-weight: bold; -fx-font-size: 14px;";
           String fieldStyle = "-fx-pref-width: 200px; -fx-font-size: 14px;";
           String comboBoxStyle = "-fx-font-size: 14px;";
           String datePickerStyle = "-fx-font-size: 14px;";

// Create Labels and Fields
           Label idLabel = new Label("ID:");
           idLabel.setStyle(labelStyle);
           Label nameLabel = new Label("Name:");
           nameLabel.setStyle(labelStyle);
           Label quantityLabel = new Label("Quantity:");
           quantityLabel.setStyle(labelStyle);
           Label priceLabel = new Label("Price:");
           priceLabel.setStyle(labelStyle);
           Label sellPriceLabel = new Label("Sell Price:");
           sellPriceLabel.setStyle(labelStyle);
           Label tradeTypeLabel = new Label("Trade Type:");
           tradeTypeLabel.setStyle(labelStyle);
           Label entryDateLabel = new Label("Entry Date:");
           entryDateLabel.setStyle(labelStyle);
           Label exitDateLabel = new Label("Exit Date:");
           exitDateLabel.setStyle(labelStyle);

// Set styles for TextFields, ComboBox, and DatePickers
           idField.setStyle(fieldStyle);
           nameField.setStyle(fieldStyle);
           quantityField.setStyle(fieldStyle);
           priceField.setStyle(fieldStyle);
           sellPriceField.setStyle(fieldStyle);
           tradeTypeComboBox.setStyle(comboBoxStyle);
           entryDatePicker.setStyle(datePickerStyle);
           exitDatePicker.setStyle(datePickerStyle);

// Add elements to GridPane
           grid.add(idLabel, 0, 0);
           grid.add(idField, 1, 0);
           grid.add(nameLabel, 0, 1);
           grid.add(nameField, 1, 1);
           grid.add(quantityLabel, 0, 2);
           grid.add(quantityField, 1, 2);
           grid.add(priceLabel, 0, 3);
           grid.add(priceField, 1, 3);
           grid.add(sellPriceLabel, 0, 4);
           grid.add(sellPriceField, 1, 4);
           grid.add(tradeTypeLabel, 0, 5);
           grid.add(tradeTypeComboBox, 1, 5);
           grid.add(entryDateLabel, 0, 6);
           grid.add(entryDatePicker, 1, 6);
           grid.add(exitDateLabel, 0, 7);
           grid.add(exitDatePicker, 1, 7);

// Set content for the dialog
           dialog.getDialogPane().setContent(grid);

// Optionally, you can set a minimum width for the dialog pane to make it more balanced
           dialog.getDialogPane().setMinWidth(450);


           ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
           dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

           dialog.setResultConverter(dialogButton -> {
               if (dialogButton == updateButtonType) {
                   selectedStockItem.setStockId(Integer.parseInt(idField.getText()));
                   selectedStockItem.setName(nameField.getText());
                   selectedStockItem.setQuantity(Integer.parseInt(quantityField.getText()));
                   selectedStockItem.setPrice(Integer.parseInt(priceField.getText()));
                   selectedStockItem.setSellPrice(Integer.parseInt(sellPriceField.getText()));

                   // Update Trade Type and Dates
                   selectedStockItem.setTradeType(tradeTypeComboBox.getValue());
                   selectedStockItem.setEntryDate(entryDatePicker.getValue().toString()); // Convert LocalDate to String
                   selectedStockItem.setExitDate(exitDatePicker.getValue().toString());   // Convert LocalDate to String

                   saveUpdatedStockItem(selectedStockItem);
                   loadStockData();
                   return selectedStockItem;
               }
               return null;
           });

           dialog.showAndWait();
       } else {
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setContentText("Please select a stock row to update.");
           alert.showAndWait();
       }
   }



    private void saveUpdatedStockItem(StockItem item) {
        String url = "jdbc:mysql://localhost:3306/trademarket";
        String user = "root";
        String password = "";

        String query = "UPDATE stock SET name = ?, quantity = ?, entry = ?, exit_ = ?, price = ?, sellPrice = ?, trade = ? WHERE idstock = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getQuantity());

            // Convert LocalDate to String (if you're using LocalDate for the dates)
            if (item.getEntryDate() != null) {
                pstmt.setString(3, item.getEntryDate().toString());  // Assuming LocalDate is used
            } else {
                pstmt.setNull(3, Types.VARCHAR);
            }

            if (item.getExitDate() != null) {
                pstmt.setString(4, item.getExitDate().toString());   // Assuming LocalDate is used
            } else {
                pstmt.setNull(4, Types.VARCHAR);
            }

            pstmt.setDouble(5, item.getPrice());  // price is double
            pstmt.setDouble(6, item.getSellPrice());  // sellPrice is double
            pstmt.setString(7, item.getTradeType());
            pstmt.setInt(8, item.getStockId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleDelete() {
        StockItem selectedStockItem = updatetable.getSelectionModel().getSelectedItem();
        if (selectedStockItem != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete StockItem Item");
            alert.setHeaderText("Are you sure you want to delete this stock item?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                deleteStockItemFromDatabase(selectedStockItem);
                loadStockData();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a stock item to delete.");
            alert.showAndWait();
        }
    }

    private void deleteStockItemFromDatabase(StockItem item) {
        String url = "jdbc:mysql://localhost:3306/trademarket";
        String user = "root";
        String password = "";

        String query = "DELETE FROM stock WHERE idstock = ?"; // Adjusted to match your StockItem

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, item.getStockId());
            pstmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @FXML
//    private void exportToCSV() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Save StockItem Data");
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
//
//        File file = fileChooser.showSaveDialog(updatetable.getScene().getWindow());
//
//        if (file != null) {
//            try (FileWriter writer = new FileWriter(file)) {
//                writer.write("ID,Name,Quantity,Entry Date,Exit Date,Price,Sell Price\n");
//                for (StockItem item : stockItemList) {
//                    writer.write(item.toCSV() + "\n"); // Ensure a newline after each item
//                }
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setContentText("StockItem data exported successfully!");
//                alert.showAndWait();
//            } catch (IOException e) {
//                e.printStackTrace();
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setContentText("Error exporting data: " + e.getMessage());
//                alert.showAndWait();
//            }
//        }
//    }

    private void loadStockData() {
        stockItemList.clear(); // Clear the list before loading new data
        String query = "SELECT idstock, name, quantity, price, sellPrice, trade, entry, exit_ FROM stock;"; // Ensure to select the right fields

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("idstock"); // Ensure this matches the column in your database
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price"); // Use double for price
                int sellPrice = rs.getInt("sellPrice"); // Use double for sellPrice
                String tradeType = rs.getString("trade");
                String entryDate = rs.getString("entry");
                String exitDate = rs.getString("exit_");

//                System.out.println("id: " + id + ", name: " + name + ", quantity: " + quantity + ", price: " + price + ", sellPrice: " + sellPrice + ", trade: " + tradeType + ", entry: " + entryDate + ", exit: " + exitDate);

                // Ensure the parameters match the constructor
                StockItem stockItem = new StockItem(id, name, quantity, price, sellPrice, tradeType, entryDate, exitDate);
                stockItemList.add(stockItem); // Add the new stock item to the list
            }

            updatetable.setItems(stockItemList); // Update the table with the new list

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveToExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(updatetable.getScene().getWindow());

        if (file != null) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Stock Data");

                // Create header row
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Stock ID");
                headerRow.createCell(1).setCellValue("Stock Name");
                headerRow.createCell(2).setCellValue("Quantity");
                headerRow.createCell(3).setCellValue("Trade Type");
                headerRow.createCell(4).setCellValue("Entry Date");
                headerRow.createCell(5).setCellValue("Exit Date");
                headerRow.createCell(6).setCellValue("Price (per piece)");
                headerRow.createCell(7).setCellValue("Sell Price");

                // Populate data rows
                int rowNum = 1;
                for (StockItem item : stockItemList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(item.getStockId());
                    row.createCell(1).setCellValue(item.getName());
                    row.createCell(2).setCellValue(item.getQuantity());
                    row.createCell(3).setCellValue(item.getTradeType());
                    row.createCell(4).setCellValue(item.getEntryDate());
                    row.createCell(5).setCellValue(item.getExitDate());
                    row.createCell(6).setCellValue(item.getPrice());
                    row.createCell(7).setCellValue(item.getSellPrice());
                }

                // Auto-size columns
                for (int i = 0; i < 8; i++) {
                    sheet.autoSizeColumn(i);
                }

                // Write the output to a file
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Data successfully saved to Excel file.");
                alert.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while saving the data to Excel.");
                alert.showAndWait();
            }
        }
    }

}