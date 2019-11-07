package MyMusic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;


    @FXML
    public void signup() {
        if (validateLogin()) {
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Use database manager to check if the entered username exists in the db, using the username and password input
            // If not add a user to the database
            DatabaseManager dbManager= null;
            try {
                dbManager = new DatabaseManager();
                // Check if another user already has the entered username
                if (dbManager.isUsernameTaken(username)) {
                    errorLabel.setText("Username is taken");
                }
                else {
                    User user = new User(-1, name, username, password, false);
                    if (dbManager.addUser(user)) {
                        PageChanger.getInstance().goToLoginPage(usernameField.getScene());
                    }
                    else {
                        errorLabel.setText("Error with database");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorLabel.setText("Error with database");
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

    public void goToLoginPage()  {
        PageChanger.getInstance().goToLoginPage(usernameField.getScene());
    }
}
