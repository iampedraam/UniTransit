package com.example.unitransit;

import com.example.unitransit.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.Map;


public class ReservationViewController implements Initializable {

    @FXML
    private TableView<Reservation> reservationTable;
    @FXML
    private TableColumn<Reservation, Integer> idColumn;
    @FXML
    private TableColumn<Reservation, String> studentColumn;
    @FXML
    private TableColumn<Reservation, String> fromColumn;
    @FXML
    private TableColumn<Reservation, String> toColumn;
    @FXML
    private TableColumn<Reservation, Integer> costColumn;

    private ReservationService reservationService;
    private Student currentStudent;
    private int fromId, toId, hour;
    private List<Road> path;

    private final Map<Integer, String> universityNames = new HashMap<>();



    public void initData(Student student, int from, int to, int hour, List<Road> path, ReservationService service) {
        this.currentStudent = student;
        this.fromId = from;
        this.toId = to;
        this.hour = hour;
        this.path = path;
    }

    private void setupUniversityNames() {
        List<University> universities = AppData.getUniversities();
        for (University u : universities) {
            universityNames.put(u.getUniversityId(), u.getName());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        studentColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudent().getUsername()));
        costColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getTotalCost()).asObject());

        reservationService = AppData.getReservationService();
        setupUniversityNames(); // ← اضافه شده

        fromColumn.setCellValueFactory(data -> {
            int fromId = data.getValue().getFrom();
            String name = universityNames.getOrDefault(fromId, "Unknown");
            return new SimpleStringProperty(name);
        });

        toColumn.setCellValueFactory(data -> {
            int toId = data.getValue().getTo();
            String name = universityNames.getOrDefault(toId, "Unknown");
            return new SimpleStringProperty(name);
        });

        if (reservationService != null) {
            reservationTable.getItems().setAll(reservationService.getAllReservations());
        }
    }



    @FXML
    private void onBackClick(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MapView.fxml"));
            Parent root = loader.load();

            MapViewController controller = loader.getController();
            controller.initData(currentStudent, fromId, toId, hour, path, reservationService);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
