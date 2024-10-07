package com.example.expense;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ComboBox;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.Group;
import java.time.LocalDate;

import javafx.scene.chart.ScatterChart;

import javafx.application.Platform;

import java.util.HashSet;
import java.util.Set;

public class MainPage {
    private static final String URL = "jdbc:mysql://localhost:3306/trademarket";
    private static final String USER = "root";
    private static final String PASSWORD = "Hassan@cr7";
    @FXML
     protected Button hbt;
    @FXML
    protected Button budtn;
    @FXML
    protected Button accbtn;
    @FXML
    protected Button rpbtn;
    @FXML
    protected Button setbtn;
    @FXML
    protected Button offbtn;
    @FXML
    protected Label dash;
    @FXML
    protected AnchorPane anch;
    @FXML
    protected BarChart<String, Number> barChart;
    @FXML
    protected ComboBox<String> monthFilter;
    @FXML
    private XYChart<String, Number> candleStickChart;

    @FXML
    protected void onOff(ActionEvent event) throws IOException {
        // Load the FXML file for the main page
        FXMLLoader fxmlLoader = new FXMLLoader(RegisterController.class.getResource("login.fxml"));

// Create the Scene using the loaded FXML content
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

// Set the Scene on the Stage
        stage.setScene(scene);

// Automatically resize the window (Stage) to fit the content in the Scene
        stage.sizeToScene();
// Set the title of the Stage
        stage.setTitle("Expense Tracker");
// Show the Stage
        stage.show();
    }
//    @FXML
//    protected void onBudget(ActionEvent event) {
//        try {
//            dash.setText("Budget");
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("Budget.fxml")); // Replace with your FXML file path
//            AnchorPane newContent = loader.load();
//
//            // Clear the current content of the AnchorPane
//            anch.getChildren().clear();
//
//            // Add the new content to the AnchorPane
//            anch.getChildren().add(newContent);
//            //             loadContent("Account.fxml");
//        } catch (IOException e) {
//            e.printStackTrace();
////
//        }
//}
        @FXML
        protected void onhome (ActionEvent event){
            try {
                dash.setText("Trade Market");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml")); // Replace with your FXML file path
                BorderPane newContent = loader.load(); // Load as BorderPane

                // Get the specific content you want to load (e.g., the Center of the BorderPane)
                Node centerContent = newContent.getCenter(); // Replace with the appropriate node you want

                // Clear the current content of the AnchorPane
                anch.getChildren().clear();

                // Add the new content (the center part) to the AnchorPane
                anch.getChildren().add(centerContent);

                //             loadContent("Account.fxml");
            } catch (IOException e) {
                e.printStackTrace();
//
            }

        }

        @FXML
        protected void OnAccnt (ActionEvent event){
            try {
                dash.setText("TradeMarket");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("account.fxml")); // Replace with your FXML file path
                AnchorPane newContent = loader.load();

                // Clear the current content of the AnchorPane
                anch.getChildren().clear();

                // Add the new content to the AnchorPane
                anch.getChildren().add(newContent);
                //             loadContent("Account.fxml");
            } catch (IOException e) {
                e.printStackTrace();
//
            }
        }

        @FXML
        private void onset (ActionEvent event){
            try {
                dash.setText("Trade Market");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("sett.fxml")); // Replace with your FXML file path
                AnchorPane newContent = loader.load();

                // Clear the current content of the AnchorPane
                
                anch.getChildren().clear();

                // Add the new content to the AnchorPane
                anch.getChildren().add(newContent);
                //             loadContent("Account.fxml");
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    @FXML
    private void onUpdate (ActionEvent event) {
        try {
            dash.setText("Trade Market");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Manipulatefinal.fxml")); // Replace with your FXML file path
            AnchorPane newContent = loader.load(); // ClassCastException if the root element is BorderPane
            // Change from AnchorPane to BorderPane

            // Clear the current content of the AnchorPane (or whatever `anch` is)
            anch.getChildren().clear();

            // Add the new content to the container (anch should be a suitable container for `BorderPane`)
            anch.getChildren().add(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private PieChart pieChart;

    @FXML
    public void initialize() {
        if (lineChart != null) {
            loadLineChartData();
        }
        if (pieChart != null) {
            loadPieChartData();
        }
        setupMonthFilter();
        loadBarChartData();
    }

    private void loadLineChartData() {
        XYChart.Series<String, Number> buySeries = new XYChart.Series<>();
        buySeries.setName("Buying Price");

        XYChart.Series<String, Number> sellSeries = new XYChart.Series<>();
        sellSeries.setName("Selling Price");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name, price, sellprice FROM stock ORDER BY entry DESC LIMIT 7")) {

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                double sellingPrice = rs.getDouble("sellprice");

                buySeries.getData().add(new XYChart.Data<>(name, price));
                sellSeries.getData().add(new XYChart.Data<>(name, sellingPrice));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        lineChart.getData().clear();
        lineChart.getData().addAll(buySeries, sellSeries);
        lineChart.setTitle("Stock Price Movement");
        ((NumberAxis) lineChart.getYAxis()).setForceZeroInRange(false);
    }


    private void loadPieChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        int totalQuantity = 0;

        // Fetch data from database
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name, quantity FROM stock")) {

            while (rs.next()) {
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                totalQuantity += quantity;
                pieChartData.add(new PieChart.Data(name, quantity));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set data to the PieChart
        pieChart.setData(pieChartData);
        pieChart.setTitle("Stock Quantity Distribution");

        // Add percentage labels
        for (PieChart.Data data : pieChartData) {
            double percentage = (data.getPieValue() / totalQuantity) * 100;
            data.nameProperty().set(data.getName() + " (" + String.format("%.1f", percentage) + "%)");
        }
    }


    private void setupMonthFilter() {
        monthFilter.getItems().addAll(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        );
        monthFilter.setValue("January");
        monthFilter.setOnAction(event -> loadBarChartData());
    }

    private void loadBarChartData() {
        String selectedMonth = monthFilter.getValue();
        int monthNumber = Month.valueOf(selectedMonth.toUpperCase()).getValue();

        XYChart.Series<String, Number> profitSeries = new XYChart.Series<>();
        profitSeries.setName("Profit");

        XYChart.Series<String, Number> lossSeries = new XYChart.Series<>();
        lossSeries.setName("Loss");

        String query = "SELECT name, SUM((sellPrice - price) * quantity) as profit_loss " +
                       "FROM stock WHERE MONTH(entry) = ? GROUP BY name ORDER BY ABS(profit_loss) DESC LIMIT 7";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, monthNumber);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                double profitLoss = rs.getDouble("profit_loss");

                if (profitLoss >= 0) {
                    profitSeries.getData().add(new XYChart.Data<>(name, profitLoss));
                } else {
                    lossSeries.getData().add(new XYChart.Data<>(name, Math.abs(profitLoss)));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        barChart.getData().clear();
        barChart.getData().addAll(profitSeries, lossSeries);

        barChart.setTitle("Profit and Loss by Stock");
        barChart.getXAxis().setLabel("Stock Name");
        barChart.getYAxis().setLabel("Amount");

        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        xAxis.setTickLabelRotation(0);

        Set<String> allStockNames = new HashSet<>();
        profitSeries.getData().forEach(data -> allStockNames.add(data.getXValue()));
        lossSeries.getData().forEach(data -> allStockNames.add(data.getXValue()));

        xAxis.setCategories(FXCollections.observableArrayList(allStockNames));

        barChart.setLegendVisible(false);

        barChart.applyCss();
        barChart.layout();

        Platform.runLater(() -> {
            for (XYChart.Series<String, Number> series : barChart.getData()) {
                String color = series.getName().equals("Profit") ? "#28a745" : "#dc3545";
                for (XYChart.Data<String, Number> data : series.getData()) {
                    if (data.getNode() != null) {
                        data.getNode().setStyle("-fx-bar-fill: " + color + ";");
                    }
                }
            }
        });


        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(calculateUpperBound(profitSeries, lossSeries));
        yAxis.setTickUnit(calculateTickUnit(yAxis.getUpperBound()));

        barChart.setBarGap(0);
        barChart.setCategoryGap(30);
    }

    private double calculateUpperBound(XYChart.Series<String, Number>... series) {
        double maxValue = 0;
        for (XYChart.Series<String, Number> s : series) {
            for (XYChart.Data<String, Number> data : s.getData()) {
                maxValue = Math.max(maxValue, data.getYValue().doubleValue());
            }
        }
        return maxValue * 1.1; // Add 10% padding
    }

    private double calculateTickUnit(double upperBound) {
        int exp = (int) Math.log10(upperBound);
        return Math.pow(10, exp - 1);
    }
}