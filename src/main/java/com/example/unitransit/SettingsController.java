package com.example.unitransit;

import com.example.unitransit.model.AppData;
import com.example.unitransit.model.Graph;
import com.example.unitransit.model.University;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.unitransit.model.Road;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class SettingsController {

    @FXML
    private ComboBox<University> GetOrigin;

    @FXML
    private ComboBox<University> GetDestination;

    @FXML
    private ComboBox<Integer> Time;

    public List<University> loadUniversitiesFromJson() {
        try {
            InputStream input = getClass().getResourceAsStream("/universities.json");
            assert input != null;
            InputStreamReader reader = new InputStreamReader(input);
            Type listType = new TypeToken<List<University>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @FXML
    public void initialize() {
        List<University> universities = loadUniversitiesFromJson();
        GetOrigin.getItems().addAll(universities);
        GetDestination.getItems().addAll(universities);
        
        for (int i = 1; i <= 24; i++) {
            Time.getItems().add(i);
        }
    }

    public List<Road> loadRoadsFromJson() {
        try {
            InputStream input = getClass().getResourceAsStream("/roads.json"); // 👈 یا فایل مسیرها
            assert input != null;
            InputStreamReader reader = new InputStreamReader(input);
            Type listType = new TypeToken<List<Road>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }



    @FXML
    private void onGoClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MapView.fxml"));
            Parent root = loader.load();

            MapViewController controller = loader.getController();

            University origin = GetOrigin.getValue();
            University destination = GetDestination.getValue();
            Integer hour = Time.getValue();

            if (origin != null && destination != null && hour != null) {
                int fromId = origin.getUniversityId();
                int toId = destination.getUniversityId();

                // پیدا کردن مسیر مناسب
                Graph graph = new Graph(AppData.getUniversities(), AppData.getRoads());
                List<Road> path = graph.bestRoute(fromId, toId);
                controller.initData(fromId, toId, hour, path);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void onBackClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
