package com.example.expense;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends Database {

    @FXML
    protected Button loginbtn;

    @FXML
    protected Label errorlabel1;

    @FXML
    protected Button registerbtn;

    @FXML
    protected TextField use; // Username field

    @FXML
    protected PasswordField pass; // Password field (hidden)

    @FXML
    protected TextField passPlain; // Password field (visible when checkbox is checked)

    @FXML
    protected CheckBox showPasswordCheckBox; // To toggle password visibility

    // Method to handle registration button action
    @FXML
    protected void onRegist(ActionEvent event) {
        try {
            // Load the registration screen (register.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = loader.load();

            // Set the new scene
            Scene newScene = new Scene(root);
            Stage stage = (Stage) registerbtn.getScene().getWindow();
            stage.setScene(newScene);

        } catch (IOException e) {
            // Handle IO exception and print the stack trace for debugging
            e.printStackTrace();
        }
    }

    // Method to handle login button action
    @FXML
    protected void onLogin(ActionEvent event) {
        try {
            // Get the username and password from the text fields
            String user = use.getText().trim();
            String password = pass.getText().trim();

            // Authenticate the user with the Database class
            boolean isAuthenticated = Database.check(user, password);

            if (isAuthenticated) {
                // If login is successful, load the main page (mainpage.fxml)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/expense/main.fxml"));
                Parent root = loader.load();

                // Set the new scene
                Scene newScene = new Scene(root);
                Stage stage = (Stage) loginbtn.getScene().getWindow();
                stage.setScene(newScene);
            } else {
                // If login fails, show error message and apply red borders to the fields
                errorlabel1.setText("Invalid username or password");
                use.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                pass.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }

        } catch (IOException e) {
            // Handle IO exception and print the stack trace for debugging
            System.out.println("Error loading scene: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // General exception handler for unexpected issues
            System.out.println("Login error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Toggle password visibility
    @FXML
    protected void togglePasswordVisibility(ActionEvent event) {
        if (showPasswordCheckBox.isSelected()) {
            // Show password in plain text
            pass.setVisible(false); // Hide PasswordField
            passPlain.setText(pass.getText()); // Copy password to plain text field
            passPlain.setVisible(true); // Show plain text field
        } else {
            // Hide password in plain text and show password field again
            pass.setText(passPlain.getText()); // Copy text back to PasswordField
            passPlain.setVisible(false); // Hide plain text field
            pass.setVisible(true); // Show PasswordField
        }
    }
}
