package com.example.unitransit;

// src/main/java/com/example/unitransit/LoginController.java

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label welcomeText;

    @FXML
    private void onLoginClick(ActionEvent event) {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (UserService.authenticate(user, pass)) {
            welcomeText.setText("خوش‌ آمدید به سامانهٔ حمل‌ونقل دانشگاه!");

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

        } else {
            welcomeText.setText("نام‌کاربری یا رمز عبور اشتباه است.");
        }
    }

    @FXML
    private void onSignupClick(ActionEvent event) throws Exception {
        javafx.scene.Parent root = javafx.fxml.FXMLLoader
                .load(getClass().getResource("Signup.fxml"));
        javafx.stage.Stage stage = (javafx.stage.Stage)
                usernameField.getScene().getWindow();
        stage.setScene(new javafx.scene.Scene(root));
    }
}
