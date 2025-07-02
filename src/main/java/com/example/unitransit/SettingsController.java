package com.example.unitransit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {

    @FXML
    private ComboBox<String> GetOrigin;

    @FXML
    private ComboBox<String> GetDestination;

    @FXML
    private TextField TimeMove;

    @FXML
    private void onGoClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MapView.fxml"));
            Parent root = loader.load();

            // دسترسی به کنترلر صفحه جدید
            MapViewController controller = loader.getController();

            // ارسال داده‌ها
            String origin = GetOrigin.getValue();
            String destination = GetDestination.getValue();
            String time = TimeMove.getText();
            controller.initData(origin, destination, time);

            // باز کردن صفحه جدید
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
