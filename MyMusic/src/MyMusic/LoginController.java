package MyMusic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import java.io.IOException;


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
    @FXML
    private void login(ActionEvent event) {
        if (validateLogin()) {
            // DB query
            String username = usernameField.getText();
            String password = passwordField.getText();
            User user = null;
            DatabaseManager dbManager= null;

            try {
                dbManager = new DatabaseManager();
                user = dbManager.getUser(username, password);
            } catch (Exception e) {
               //e.printStackTrace();
            }

            if (user != null) {
                errorLabel.setText("");
                changePage("fxml/home.fxml");
            }
            else {
                changePage("fxml/home.fxml");
                errorLabel.setText("You have entered an invalid username or password");
            }
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

    public void changePage(String fxmlPath)  {
        try {
            Parent root = null;
            root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = usernameField.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
