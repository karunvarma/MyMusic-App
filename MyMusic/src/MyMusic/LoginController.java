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
            String username = usernameField.getText();
            String password = passwordField.getText();
            User user = null;

            // Use database manager to get a user, using the username and password input
            DatabaseManager dbManager= null;
            try {
                dbManager = new DatabaseManager();
                user = dbManager.getUser(username, password);
            } catch (Exception e) {
               e.printStackTrace();
            }

            if (user != null) {
                // If a user is retrieved login
                errorLabel.setText("");
                goToHomePage(user);
            }
            else {
                // If no user is returned
                errorLabel.setText("You have entered an invalid username or password");
            }
        }
        else {
            // If one or both of username or password fields is empty
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

    public void goToHomePage(User user)  {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/home.fxml"));
            root = loader.load();

            // Get the controller of the new root
            Controller controller = loader.getController();

            // Set user property of the controller
            controller.setUser(user);

            Scene scene = usernameField.getScene();

            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
