package com.example.unitransit;

// src/main/java/com/example/unitransit/LoginController.java

import com.example.unitransit.model.*;
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
import java.util.List;
import java.util.Random;

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
            //Load
            List<University> universities = datamanagement.loadUniversity();
            AppData.setUniversities(universities);
            List<Road> roads = Road.generateFixedRoads(new Random());
            Graph fullGraph = new Graph(universities, roads);
            List<Road> mstEdges = fullGraph.computeMST();
            Graph mstGraph = new Graph(universities, mstEdges);
            if (mstGraph.allPairsWithinTwoStops()) {
                AppData.setGraph(mstGraph);
            } else {
                AppData.setGraph(fullGraph);
            }

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
