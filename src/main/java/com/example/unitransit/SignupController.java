// src/main/java/com/example/unitransit/SignupController.java
package com.example.unitransit;

import com.example.unitransit.model.Student;
import com.example.unitransit.Datamanagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignupController {
    @FXML private TextField FirstName;
    @FXML private TextField LastName;
    @FXML private TextField PhoneNumber;
    @FXML private TextField university;
    @FXML private TextField StudentNumber;
    @FXML private TextField UserName;
    @FXML private PasswordField Password;
    @FXML private Label welcomeText;

    private Datamanagement datamanagement;
    public void initialize() {
        datamanagement = new Datamanagement();
    }

    @FXML
    private void onSignupClick(ActionEvent event) throws Exception {
        String firstNameText = FirstName.getText();
        String lastNameText = LastName.getText();
        String phoneNumberText = PhoneNumber.getText();
        String universityText = university.getText();
        String studentNumberText = StudentNumber.getText();
        String userNameText = UserName.getText();
        String passwordText = Password.getText();

        if (firstNameText.isEmpty() || lastNameText.isEmpty() || phoneNumberText.isEmpty() ||
                universityText.isEmpty() || studentNumberText.isEmpty() ||
                userNameText.isEmpty() || passwordText.isEmpty()) {
            welcomeText.setText("Some fields are empty. Please fill out the form completely.");
            return;
        }

        List<Student> students = datamanagement.loadStudents();
        if (students.isEmpty()) {
            students = new ArrayList<>();
        }
        boolean studentExists = false;
        for (Student student : students) {
            if (student.getUsername().equals(userNameText)) {
                studentExists = true;
                break;
            }
        }
        if (studentExists) {
            welcomeText.setText("This username is already taken. Please choose a different username.");
            return;
        }
        Student st = new Student(
                UserName.getText(),
                Password.getText(),
                FirstName.getText(),
                LastName.getText(),
                StudentNumber.getText(),
                university.getText(),
                PhoneNumber.getText()
        );

        students.add(st);
        datamanagement.saveStudents(students);

        welcomeText.setText("Registration completed successfully!");
        welcomeText.setStyle("-fx-text-fill: #186cd9;");

        javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2));
        delay.setOnFinished(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        delay.play();
    }

    @FXML
    private void onLoginClick(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
