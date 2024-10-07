package com.example.expense;

import com.example.expense.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class RegisterController extends Database {

    @FXML
    protected Button btn1;

    @FXML
    protected TextField txt1; // Username
    @FXML
    protected TextField txt2; // Email

    @FXML
    protected PasswordField txt3; // Password (hidden)
    @FXML
    protected PasswordField txt4; // Confirm password

    @FXML
    protected TextField txt3Plain; // Password (plain text, visible when checkbox is selected)
    @FXML
    protected TextField txt4Plain; // Confirm password (plain text)

    @FXML
    protected CheckBox showPasswordCheckBox; // To toggle password visibility

    @FXML
    protected boolean validateInput() {
        boolean isValid = true;

        // Validation for txt1: Only Alphabets, No Spaces
        if (!txt1.getText().trim().matches("[a-zA-Z]+")) {
            txt1.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            txt1.setStyle(null); // Reset border color if valid
        }

        // Validation for txt2: Email Validation using regex
        if (!txt2.getText().trim().matches("^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            txt2.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            isValid = false; // Validation failed for email
        } else {
            txt2.setStyle(null); // Reset style if valid
        }

        // Validation for txt3: Password (min 8 chars, 1 uppercase, 1 lowercase, 1 number, 1 special char)
        if (!txt3.getText().trim().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$")) {
            txt3.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            isValid = false;
        } else {
            txt3.setStyle(null);
        }

        // Confirm password validation
        if (!txt4.getText().trim().equals(txt3.getText())) {
            txt4.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            isValid = false;
        } else {
            txt4.setStyle(null);
        }

        return isValid;
    }

    // Toggle password visibility
    @FXML
    protected void togglePasswordVisibility(ActionEvent event) {
        if (showPasswordCheckBox.isSelected()) {
            // Show password in plain text
            txt3.setVisible(false); // Hide PasswordField
            txt4.setVisible(false); // Hide ConfirmPasswordField

            txt3Plain.setText(txt3.getText()); // Copy password to plain text field
            txt4Plain.setText(txt4.getText()); // Copy confirm password to plain text field
            txt3Plain.setVisible(true); // Show plain text field
            txt4Plain.setVisible(true); // Show plain text field
        } else {
            // Hide password in plain text and show password field again
            txt3.setText(txt3Plain.getText()); // Copy text back to PasswordField
            txt4.setText(txt4Plain.getText()); // Copy text back to PasswordField

            txt3Plain.setVisible(false); // Hide plain text field
            txt4Plain.setVisible(false); // Hide plain text field

            txt3.setVisible(true); // Show PasswordField
            txt4.setVisible(true); // Show ConfirmPasswordField
        }
    }

    @FXML
    protected void onOpen(ActionEvent event) {
        if (validateInput()) {
            String user = txt1.getText().trim();
            String password = txt2.getText().trim();
            String mail = txt3.getText().trim();
            String confirm = txt4.getText().trim();
            Database.insert(user, password, mail, confirm);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent root = loader.load();
                Scene newScene = new Scene(root);
                Stage stage = (Stage) btn1.getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
