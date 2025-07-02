package com.example.unitransit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapViewController {

    @FXML
    private Pane mapPane; // باید در FXML تعریف شود

    @FXML
    private ImageView iranMap;

    private String origin;
    private String destination;
    private String time;

    private final Map<String, double[]> universityPositions = new HashMap<>();

    public void initData(String origin, String destination, String time) {
        this.origin = origin;
        this.destination = destination;
        this.time = time;

        setupUniversityPositions();
        drawUniversities();
        drawRouteBetweenUniversities();
    }

    private void setupUniversityPositions() {
        universityPositions.put("University of Tehran", new double[]{265, 170});
        universityPositions.put("University of Isfahan", new double[]{270, 270});
        universityPositions.put("University of Guilan", new double[]{210, 120});
        universityPositions.put("University of Shiraz", new double[]{320, 435});
        universityPositions.put("Ferdowsi University of Mashhad", new double[]{530, 150});
        universityPositions.put("Semnan University", new double[]{355, 170});
        universityPositions.put("University of Mazandaran", new double[]{320, 130});
        universityPositions.put("Sahand University of Technology", new double[]{88, 83});
        universityPositions.put("Persian Gulf University", new double[]{240, 420});
    }

    private void drawUniversities() {
        for (Map.Entry<String, double[]> entry : universityPositions.entrySet()) {
            double[] pos = entry.getValue();
            String name = entry.getKey();

            Circle circle = new Circle(pos[0], pos[1], 7, Color.ORANGE);
            circle.setStroke(Color.DARKBLUE);
            circle.setStrokeWidth(2);

            Tooltip tooltip = new Tooltip(name);
            Tooltip.install(circle, tooltip);

            // Hover effect: بزرگ شدن
            circle.setOnMouseEntered(e -> {
                circle.setRadius(10); // بزرگتر شدن دایره
                circle.setFill(Color.DARKORANGE); // تغییر رنگ دلخواه
            });

            // بازگشت به حالت اولیه
            circle.setOnMouseExited(e -> {
                circle.setRadius(7); // اندازه اولیه
                circle.setFill(Color.ORANGE); // رنگ اولیه
            });

            mapPane.getChildren().add(circle);
        }
    }

    private void drawRouteBetweenUniversities() {
        double[] start = universityPositions.get(origin);
        double[] end = universityPositions.get(destination);

        if (start != null && end != null) {
            Line line = new Line(start[0], start[1], end[0], end[1]);
            line.setStroke(Color.BLUE);
            line.setStrokeWidth(3);

            mapPane.getChildren().add(line);
        }
    }

    @FXML
    private void onBackClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
