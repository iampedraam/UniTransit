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

        //اینجا باید چک کنه نام کاربری و رمز قبلا ایجاد شده تو پایگاه داده یا نه. اگه شده بود اطلاعات رو کامل دریافت کنه و یک object جدید ایجاد کنه بعد وارد سیستم بشه
//        if (){
//
//        }else {
//
//        }
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
