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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import optic.light.AgregatedResult;
import optic.experiments.interference.YoungExperiment;
import optic.light.IntensityAgregator;
import optic.light.LightBeam;
import optic.light.RadiationType;
import optic.light.Wave;

import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class YoungOverviewController {
    //char lambda = 'λ';
    @FXML
    private Canvas canvas;
    @FXML
    private ScatterChart<?, ?> chart;
    @FXML
    private CategoryAxis axisX;
    @FXML
    private NumberAxis axisIntense;
    @FXML
    private Button button;
    @FXML
    private TextField minL;
    @FXML
    private TextField maxL;
    @FXML
    private Label label;
    @FXML
    private Canvas spector;
    @FXML
    private RadioButton radMono;
    @FXML
    private RadioButton radBi;
    @FXML
    private RadioButton radSpecter;
    @FXML
    private Label lblMin;
    @FXML
    private Label lblMax;
    @FXML
    private TextField txtLengthL;
    @FXML
    private TextField txtLengthD;
    @FXML
    private Canvas canvasScale;
    @FXML
    private TextField txtScreenSize;
    @FXML
    private Label lblScreenSize;

    LightBeam lightBeam;
    double D = 0.000_001;
    double L = 0.5;
    double screenL = 2.0;


    @FXML
    void initialize() {
        minL.setText("380");
        maxL.setText("780");
        chart.getStylesheets().add("/style/chartPoints.css");
        lightBeam = new LightBeam(RadiationType.SPECTER, 380, 780);
        radSpecter.setSelected(true);
        drawSpecter();
        getSpecter();
    }

    void drawSpecter() {
        GraphicsContext gc = spector.getGraphicsContext2D();
        gc.setFill(Paint.valueOf("#000000"));
        gc.fillRect(0, 0, spector.getWidth(), spector.getHeight());

        if (lightBeam.getRadiationType() == RadiationType.SPECTER)
            for (int wave = lightBeam.getFirstWaveLength(); wave <= lightBeam.getSecondWaveLength(); wave++) {
                gc.setFill(Wave.getRGB(wave));
                gc.fillRect((wave - Wave.MIN_WAVE_LENGTH) / 2, 0, 1, spector.getHeight());
            }
        else if (lightBeam.getRadiationType() == RadiationType.MONOCHROMATIC) {
            gc.setFill(Wave.getRGB(lightBeam.getFirstWaveLength()));
            gc.fillRect((lightBeam.getFirstWaveLength() - Wave.MIN_WAVE_LENGTH) / 2, 0, 1, spector.getHeight());
        } else if (lightBeam.getRadiationType() == RadiationType.BICHROMATIC) {
            if (lightBeam.getSecondWaveLength() - lightBeam.getFirstWaveLength() > 1) {
                gc.setFill(Wave.getRGB(lightBeam.getFirstWaveLength()));
                gc.fillRect((lightBeam.getFirstWaveLength() - Wave.MIN_WAVE_LENGTH) / 2, 0, 1, spector.getHeight());
                gc.setFill(Wave.getRGB(lightBeam.getSecondWaveLength()));
                gc.fillRect((lightBeam.getSecondWaveLength() - Wave.MIN_WAVE_LENGTH) / 2, 0, 1, spector.getHeight());
            } else {
                gc.setFill(Wave.getRGB(lightBeam.getFirstWaveLength()));
                gc.fillRect((lightBeam.getFirstWaveLength() - Wave.MIN_WAVE_LENGTH) / 2, 0, 1, spector.getHeight());
                gc.setFill(Wave.getRGB(lightBeam.getSecondWaveLength()));
                gc.fillRect((lightBeam.getSecondWaveLength() - Wave.MIN_WAVE_LENGTH) / 2, 0, 1, spector.getHeight());
            }
        }

    }

    @FXML
    void getSpecter() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Paint.valueOf("#000000"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        final int height = (int) canvas.getHeight();

        YoungExperiment exp = new YoungExperiment();

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

        lightBeam.setFirstWaveLength(min);
        lightBeam.setSecondWaveLength(max);

        exp.setLightBeam(lightBeam);
        exp.setD(D);
        exp.setL(L);
        exp.setScreenL(screenL);

        int half = (int) canvas.getWidth() / 2;

        Map<Integer, List<Double>> intensity = exp.getSpecter(half);

        AgregatedResult result = IntensityAgregator.agregate(intensity);

        for (int i = 0; i < result.getColors().length; i++) {
            gc.setFill(result.getColors()[i]);
            gc.fillRect(half + i, 0, 1, height);
            gc.fillRect(half - i - 1, 0, 1, height);
        }

        XYChart.Series series = new XYChart.Series();
        for (int i = result.getIntensity().length - 1; i > 0; i--) {
            series.getData().add(new XYChart.Data<>(String.valueOf(-i), 2 * result.getIntensity()[i] - 1));
        }
        for (int i = 0; i < result.getIntensity().length; i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i), 2 * result.getIntensity()[i] - 1));
        }

        drawSpecter();
        chart.getData().clear();
        chart.getData().addAll(series);
    }

    @FXML
    void clickBi() {
        radBi.setDisable(true);
        radSpecter.setDisable(true);
        radMono.setDisable(true);

        synchronized (lightBeam) {
            radSpecter.setSelected(false);
            radMono.setSelected(false);
            radBi.setSelected(true);

            lightBeam.setRadiationType(RadiationType.BICHROMATIC);

            lblMin.setText("λ(первая)");
            lblMax.setText("λ(вторая)");

            maxL.setText(String.valueOf(lightBeam.getSecondWaveLength()));
            minL.setText(String.valueOf(lightBeam.getFirstWaveLength()));

            lblMax.setVisible(true);
            maxL.setVisible(true);

            redrawAll();
        }

        radBi.setDisable(false);
        radSpecter.setDisable(false);
        radMono.setDisable(false);
    }

    @FXML
    void clickMono() {
        radBi.setDisable(true);
        radSpecter.setDisable(true);
        radMono.setDisable(true);

        synchronized (lightBeam) {
            radSpecter.setSelected(false);
            radMono.setSelected(true);
            radBi.setSelected(false);

            lightBeam.setRadiationType(RadiationType.MONOCHROMATIC);
            lblMin.setText("λ");

            maxL.setText(String.valueOf(lightBeam.getSecondWaveLength()));
            minL.setText(String.valueOf(lightBeam.getFirstWaveLength()));

            lblMax.setVisible(false);
            maxL.setVisible(false);

            redrawAll();
        }

        radBi.setDisable(false);
        radSpecter.setDisable(false);
        radMono.setDisable(false);
    }

    @FXML
    void clickSpecter() {
        radBi.setDisable(true);
        radSpecter.setDisable(true);
        radMono.setDisable(true);

        synchronized (lightBeam) {
            radSpecter.setSelected(true);
            radMono.setSelected(false);
            radBi.setSelected(false);

            lightBeam.setRadiationType(RadiationType.SPECTER);
            maxL.setText(String.valueOf(lightBeam.getSecondWaveLength()));
            minL.setText(String.valueOf(lightBeam.getFirstWaveLength()));

            lblMin.setText("λ(мин.)");
            lblMax.setText("λ(макс.)");

            lblMax.setVisible(true);
            maxL.setVisible(true);

            redrawAll();
        }

        radBi.setDisable(false);
        radSpecter.setDisable(false);
        radMono.setDisable(false);
    }

    private void redrawAll() {
        drawSpecter();
        getSpecter();
    }

    private void initKeyBindings() {

    }

}
