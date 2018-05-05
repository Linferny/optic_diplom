package main;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import optic.experiments.AgregatedResult;
import optic.experiments.YoungInterferenceExp;
import optic.light.IntensityAgregator;
import optic.light.LightBeam;
import optic.light.RadiationType;

import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class OpticOverviewController {
    @FXML
    Canvas canvas;
    @FXML
    Button button;
    @FXML
    LineChart<Double, Double> chart;
    @FXML
    Label label;
    @FXML
    TextField minL;
    @FXML
    TextField maxL;


    @FXML
    void initialize() {

    }

    @FXML
    void testCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Paint.valueOf("#000000"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        final int height = (int) canvas.getHeight();

        YoungInterferenceExp exp = new YoungInterferenceExp();

        int min = 500;
        int max = 700;
        try {
            min = Integer.parseInt(minL.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            max = Integer.parseInt(maxL.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        exp.setLightBeam(new LightBeam(RadiationType.RECTANGULAR, min, max));
        exp.setD(0.000_001);
        exp.setL(0.5);

        int half = (int) canvas.getWidth() / 2;

        Map<Integer, List<Double>> intensity = exp.getSpecter(half);

        AgregatedResult result = IntensityAgregator.agregate(intensity);

        for (int i = 0; i < result.getColors().length; i++) {
            gc.setFill(result.getColors()[i]);
            gc.fillRect(half + i, 0, 1, height);
            gc.fillRect(half - i - 1, 0, 1, height);
        }
    }
}
