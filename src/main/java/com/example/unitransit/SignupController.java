// src/main/java/com/example/unitransit/SignupController.java
package com.example.unitransit;

import com.example.unitransit.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class SignupController {
    @FXML private TextField FirstName;
    @FXML private TextField LastName;
    @FXML private TextField PhoneNumber;
    @FXML private TextField university;
    @FXML private TextField StudentNumber;
    @FXML private TextField UserName;
    @FXML private PasswordField Password;

    // Task: بررسی اینکه نام کاربری در فایل تکراری ثبت نشه
    @FXML
    private void onSignupClick(ActionEvent event) throws Exception {
        Student st = new Student(
                UserName.getText(),
                Password.getText(),
                FirstName.getText(),
                LastName.getText(),
                StudentNumber.getText(),
                university.getText(),
                PhoneNumber.getText()
        );

        // بعد از ثبت‌نام مستقیم بازگردیم به صفحه ورود
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void onLoginClick(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
