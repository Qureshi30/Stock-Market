package com.example.expense;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    // Constants for database connection
    private static final String URL = "jdbc:mysql://localhost:3306/trademarket";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Connection object
    private static Connection conn = null;

    // Method to establish connection to the database
    public static void connect() {
        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection established successfully.");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }

    // Method to insert data into the database
    public static void insert(String user, String mail, String pass, String conf) {
        String query = "INSERT INTO users (username, password, confirm,email) VALUES (?, ?, ?, ?)";

        try {
            // Prepare the SQL statement
            PreparedStatement pstmt = conn.prepareStatement(query);

            // Set the values for the query
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            pstmt.setString(3, conf);
            pstmt.setString(4, mail);

            // Execute the query to insert data
            pstmt.executeUpdate();
            System.out.println("Data inserted successfully!");

        } catch (SQLException e) {
            System.out.println("Exception occurred during insertion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to check user credentials


    // Method to close the connection
    public static void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection.");
            e.printStackTrace();
        }
    }
    protected  static boolean check(String user, String password) {
        String user2= user;
        String password2 = password;
        String query = "SELECT * FROM users WHERE username = ?  And password = ? ";
        try{
            // Prepare the SQL statement with placeholders for parameters
            PreparedStatement pstmt = conn.prepareStatement(query);

            // Set the values for the query
            pstmt.setString(1, user2);      // Set the first placeholder for username
            pstmt.setString(2, password2);  // Set the second placeholder for password

            // Execute the query and get the result
            ResultSet rs = pstmt.executeQuery();

            // Check if a record exists (i.e., user credentials match)
            if (rs.next()) {
                // User found, return true for a successful login
                System.out.println("Login successful! Welcome, " + user2);
                return true;
            } else {
                // No matching user found, return false for login failure
                System.out.println("Login failed. Invalid username or password.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
            return false;  // Return false if an exception occurs
        }

    }
}

