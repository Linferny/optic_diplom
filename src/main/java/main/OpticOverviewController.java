package main;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import optic.experiments.YoungInterferenceExp;
import optic.light.Wave;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class OpticOverviewController {
    @FXML
    Canvas canvas;
    @FXML
    Button button;

    @FXML
    void initialize() {

    }

    @FXML
    void testCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Paint.valueOf("#000000"));
        gc.setLineWidth(1);
        gc.fillRect(0, 0, 400, 400);

        YoungInterferenceExp exp = new YoungInterferenceExp();

        List<Double> intense = exp.getLength();

        int l = intense.size() / 2;

        for (int i = l; i < intense.size(); i++) {
            gc.setFill(Wave.getRGB(720, intense.get(i)));
            gc.fillRect(i, 0, 1, 100);

            gc.setFill(Wave.getRGB(720, intense.get(i - l)));
            gc.fillRect(i - l, 0, 1, 100);
        }
    }
}
