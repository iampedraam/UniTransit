package com.example.unitransit;

// src/main/java/com/example/unitransit/LoginController.java

import javafx.event.ActionEvent;
import com.example.unitransit.Datamanagement;
import com.example.unitransit.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label welcomeText;

    private Datamanagement datamanagement;

    public void initialize(){
        datamanagement = new Datamanagement();
    }

    @FXML
    private void onLoginClick(ActionEvent event) {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            welcomeText.setText("Username or password not entered.");
            return;
        }

        List<Student> students = datamanagement.loadStudents();

        boolean isFound = false;
        Student loggedStudent = null;
        for (Student student : students) {
            if (student.getUsername().equals(user) && student.getPassword().equals(pass)) {
                isFound = true;
                loggedStudent = student;
                break;
            }
        }

        if (isFound) {
            welcomeText.setText("Welcome " + user);
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
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
