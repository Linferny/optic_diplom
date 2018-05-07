package controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import optic.experiments.interference.AgregatedResult;
import optic.experiments.interference.NewtonExperiment;
import optic.experiments.interference.YoungExperiment;
import optic.light.IntensityAgregator;
import optic.light.LightBeam;
import optic.light.RadiationType;

import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewtonOverviewController {
    @FXML
    Canvas canvas;
    @FXML
    Button button;
    @FXML
    private ScatterChart<?, ?> chart;
    @FXML
    private CategoryAxis axisX;
    @FXML
    private NumberAxis axisIntense;
    @FXML
    Label label;
    @FXML
    TextField minL;
    @FXML
    TextField maxL;


    @FXML
    void initialize() {
        minL.setText("400");
        maxL.setText("500");
        chart.getStylesheets().add("/style/chartPoints.css");
    }

    @FXML
    void getSpecter() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Paint.valueOf("#000000"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(1);
        final int height = (int) canvas.getHeight();

        NewtonExperiment exp = new NewtonExperiment();

        int min = 0;
        int max = 0;
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
        exp.setR(0.01);
        exp.setScreenL(0.0002);

        int half = (int) canvas.getWidth() / 2;

        Map<Integer, List<Double>> intensity = exp.getSpecter(half);

        AgregatedResult result = IntensityAgregator.agregate(intensity);

        for (int i = result.getColors().length - 1; i >= 0; i--) {
            gc.setFill(result.getColors()[i]);
            gc.fillOval(half - i, half - i, i * 2, i * 2);
        }

        XYChart.Series series = new XYChart.Series();
        for (int i = result.getIntensity().length - 1; i > 0; i--) {
            series.getData().add(new XYChart.Data<>(String.valueOf(-i), 2 * result.getIntensity()[i] - 1));
        }
        for (int i = 0; i < result.getIntensity().length; i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i), 2 * result.getIntensity()[i] - 1));
        }

        chart.getData().clear();
        chart.getData().addAll(series);
    }
}
