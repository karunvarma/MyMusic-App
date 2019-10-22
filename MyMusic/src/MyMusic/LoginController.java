package MyMusic;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.awt.*;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    // Method called when the login button is pressed.
    // Gets username and password from login.fxml.
    // Validates user input. If invalid, display error message.
    // Validates username and password with the database.
    // If credentials are valid, log user in, else display error message.
    public void login() {
        if (validateLogin()) {
            // DB query
            String username = usernameField.getText();
            String password = passwordField.getText();
            errorLabel.setText("");
        }
        else {
            errorLabel.setText("You have entered an invalid username or password");
        }
    }

    private boolean validateLogin() {
        Boolean isValid = true;
        if (usernameField.getText().equals("")) {
            usernameField.setStyle("-fx-border-color: red");
            isValid = false;
        }
        else {
            usernameField.setStyle("-fx-border-color: none");
        }

        if (passwordField.getText().equals("")) {
            passwordField.setStyle("-fx-border-color: red");
            isValid = false;
        }
        else {
            passwordField.setStyle("-fx-border-color: none");
        }

        return isValid;
    }
}
