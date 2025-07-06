package com.example.unitransit;

import com.example.unitransit.model.Road;
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
import java.util.List;
import java.util.Map;

public class MapViewController {

    @FXML
    private Pane mapPane; // باید در FXML تعریف شود

    @FXML
    private ImageView iranMap;

    private int origin;
    private int destination;
    private int time;
    private int fromId, toId, hour;
    private List<Road> path;

    private final Map<Integer, double[]> universityPositions = new HashMap<>();
    private final Map<Integer, String> universityNames = new HashMap<>();

    public void initData(int originId, int destinationId, int hour, List<Road> path) {
        this.origin = originId;
        this.destination = destinationId;
        this.time = hour;
        this.path = path;

        setupUniversityPositions();
        drawUniversities();
        drawRouteBetweenUniversities();
    }

    private void setupUniversityPositions() {
        universityPositions.put(1, new double[]{210, 120}); // University of Guilan
        universityPositions.put(2, new double[]{265, 170}); // University of Tehran
        universityPositions.put(3, new double[]{270, 270}); // University of Isfahan
        universityPositions.put(4, new double[]{320, 435}); // University of Shiraz
        universityPositions.put(5, new double[]{530, 150}); // Ferdowsi University of Mashhad
        universityPositions.put(6, new double[]{320, 130}); // University of Mazandaran
        universityPositions.put(7, new double[]{88, 83});   // University of Tabriz

        universityNames.put(1, "University of Guilan");
        universityNames.put(2, "University of Tehran");
        universityNames.put(3, "University of Isfahan");
        universityNames.put(4, "University of Shiraz");
        universityNames.put(5, "Ferdowsi University of Mashhad");
        universityNames.put(6, "University of Mazandaran");
        universityNames.put(7, "University of Tabriz");
    }

    private void drawUniversities() {
        for (Map.Entry<Integer, double[]> entry : universityPositions.entrySet()) {
            int uniId = entry.getKey();
            double[] pos = entry.getValue();

            Circle circle = new Circle(pos[0], pos[1], 7, Color.ORANGE);
            circle.setStroke(Color.DARKBLUE);
            circle.setStrokeWidth(2);

            // دریافت نام از Map
            String name = universityNames.getOrDefault(uniId, "University ID: " + uniId);
            Tooltip tooltip = new Tooltip(name);
            Tooltip.install(circle, tooltip);

            circle.setOnMouseEntered(e -> {
                circle.setRadius(10);
                circle.setFill(Color.DARKORANGE);
            });

            circle.setOnMouseExited(e -> {
                circle.setRadius(7);
                circle.setFill(Color.ORANGE);
            });

            mapPane.getChildren().add(circle);
        }
    }

    private void drawRouteBetweenUniversities() {
        if (path == null || path.isEmpty()) return;

        for (Road road : path) {
            double[] start = universityPositions.get(road.getFrom());
            double[] end = universityPositions.get(road.getTo());

            if (start == null || end == null) continue;

            Line line = new Line(start[0], start[1], end[0], end[1]);

            boolean isOpen = time >= road.getOpen() && time <= road.getClose();
            line.setStroke(isOpen ? Color.BLUE : Color.GRAY);
            line.setStrokeWidth(4);

            Tooltip tooltip = new Tooltip(
                    "From: " + universityNames.getOrDefault(road.getFrom(), "ID " + road.getFrom()) + "\n" +
                            "To: " + universityNames.getOrDefault(road.getTo(), "ID " + road.getTo()) + "\n" +
                            "Price: " + road.getPrice() + "\n" +
                            "Capacity: " + road.getCapacity() + "\n" +
                            "Time Window: " + road.getOpen() + " - " + road.getClose() + "\n" +
                            (isOpen ? "🟢 Road is open" : "🔴 Road is closed")
            );
            Tooltip.install(line, tooltip);

            mapPane.getChildren().add(line);
        }
    }




//    private void drawRouteBetweenUniversities() {
//        double[] start = universityPositions.get(origin);
//        double[] end = universityPositions.get(destination);
//
//        if (start != null && end != null) {
//            Line line = new Line(start[0], start[1], end[0], end[1]);
//
//            // Task: باید تک تک جاده های path بررسی بشه
//            if (selectedRoad != null) {
//                // بررسی باز بودن مسیر در ساعت انتخاب شده
//                boolean isOpen = time >= selectedRoad.getOpen() && time <= selectedRoad.getClose();
//
//                // تنظیم رنگ و توضیح مسیر
//                line.setStroke(isOpen ? Color.BLUE : Color.ORANGE);
//                line.setStrokeWidth(4);
//
//                Tooltip tooltip = new Tooltip(
//                        "Price: " + selectedRoad.getPrice() + "\n" +
//                                "capacity: " + selectedRoad.getCapacity() + "\n" +
//                                "Time: " + selectedRoad.getOpen() + " - " + selectedRoad.getClose() + "\n" +
//                                (isOpen ? "🟢 The road is open." : "🔴 The road is blocked.")
//                );
//                Tooltip.install(line, tooltip);
//
//            } else {
//                line.setStroke(Color.GRAY);
//                line.setStrokeWidth(3);
//            }
//
//            mapPane.getChildren().add(line);
//        }
//    }

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
