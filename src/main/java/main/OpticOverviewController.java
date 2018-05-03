package main;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import optic.experiments.YoungInterferenceExp;
import optic.light.LightBeam;
import optic.light.RadiationType;
import optic.light.Wave;

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
    private boolean blend = true;
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
            return;
        }
        try {
            max = Integer.parseInt(maxL.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        exp.setLightBeam(new LightBeam(RadiationType.BICHROMATIC, min, max));
        exp.setD(0.000_001);
        exp.setL(0.5);

        int half = (int) canvas.getWidth() / 2;
        if (blend) {
            label.setText("Blend");
        } else label.setText("Not blended");

        boolean test = true;

        if (test) {
            Map<Integer, List<Double>> intensity = exp.getBichrom2(half);

            int w1 = exp.getLightBeam().getFirstWaveLength();
            int w2 = exp.getLightBeam().getSecondWaveLength();

            if (exp.getLightBeam().getRadiationType() == RadiationType.BICHROMATIC)
                for (int i = 0; i < half; i++) {

                    double i1 = intensity.get(w1).get(i);
                    double i2 = intensity.get(w2).get(i);

                    Color c1 = Wave.getRGB(w1, i1);
                    Color c2 = Wave.getRGB(w2, i2);

                    if (blend) {
                        Color c = blend(c1, c2);

                        gc.setFill(c);
                        gc.fillRect(half + i, 0, 1, height);
                        gc.fillRect(half - i - 1, 0, 1, height);

                    } else {

                        gc.setFill(c1);
                        gc.fillRect(half + i, 0, 1, height);
                        gc.fillRect(half - i - 1, 0, 1, height);

                        gc.setFill(c2);
                        gc.fillRect(half + i, 0, 1, height);
                        gc.fillRect(half - i - 1, 0, 1, height);

                    }

                }
            blend = !blend;
        } else {
            List<Double> intensity = exp.getBichrom(half);

            int w1 = exp.getLightBeam().getFirstWaveLength();
            int w2 = exp.getLightBeam().getSecondWaveLength();

            if (exp.getLightBeam().getRadiationType() == RadiationType.BICHROMATIC)
                for (int i = 0; i < half; i++) {

                    double i1 = intensity.get(i);

                    Color c1 = Wave.getRGB(w1, i1);
                    Color c2 = Wave.getRGB(w2, i1);

                    gc.setFill(c1);
                    gc.fillRect(half + i, 0, 1, height);
                    gc.fillRect(half - i - 1, 0, 1, height);

                    gc.setFill(c2);
                    gc.fillRect(half + i, 0, 1, height);
                    gc.fillRect(half - i - 1, 0, 1, height);
                }
        }
    }

    public static Color blend(Color c0, Color c1) {
        double totalAlpha = c0.getOpacity() + c1.getOpacity();
        double weight0 = c0.getOpacity() / totalAlpha;
        double weight1 = c1.getOpacity() / totalAlpha;

        double r = weight0 * c0.getRed() + weight1 * c1.getRed();
        double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
        double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
        double a = Math.max(c1.getOpacity(), c0.getOpacity());
        //double a = c0.getOpacity() + c1.getOpacity() > 1 ? 1.0 : c0.getOpacity() + c1.getOpacity();

        return new Color(r, g, b, a);
    }

    void test() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Paint.valueOf("#000000"));
        gc.setLineWidth(1);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        YoungInterferenceExp exp = new YoungInterferenceExp();

        exp.setLightBeam(new LightBeam(RadiationType.BICHROMATIC, 380, 500));
        exp.setD(0.000_001);
        exp.setL(0.01);

        Map<Integer, List<Double>> intense = exp.getSpector();

        final int height = (int) canvas.getHeight();

        int half = (int) canvas.getWidth() / 2;

        if (exp.getLightBeam().getRadiationType() == RadiationType.BICHROMATIC)
            for (int i = 0; i < half; i++) {
                double correction1 = (intense.get(exp.getLightBeam().getFirstWaveLength()).size() * 2) / canvas.getWidth();
                double correction2 = (intense.get(exp.getLightBeam().getSecondWaveLength()).size() * 2) / canvas.getWidth();

                double i1 = intense.get(exp.getLightBeam().getFirstWaveLength()).get((int) (correction1 * i));
                double i2 = intense.get(exp.getLightBeam().getSecondWaveLength()).get((int) (correction2 * i));

                gc.setFill(Wave.getRGB(exp.getLightBeam().getFirstWaveLength(), i1));
                gc.fillRect(half + i, 0, 1, height);
                gc.fillRect(half - i - 1, 0, 1, height);

                gc.setFill(Wave.getRGB(exp.getLightBeam().getSecondWaveLength(), i1));
                gc.fillRect(half + i, 0, 1, height);
                gc.fillRect(half - i - 1, 0, 1, height);
            }


        if (exp.getLightBeam().getRadiationType() == RadiationType.MONOCHROMATIC)
            for (int i = 0; i < half; i++) {
                int size = intense.get(exp.getLightBeam().getFirstWaveLength()).size();
                double correction = (size * 2) / canvas.getWidth();
                double intensity = intense.get(exp.getLightBeam().getFirstWaveLength()).get((int) (correction * i));
                gc.setFill(Wave.getRGB(exp.getLightBeam().getFirstWaveLength(), intensity));
                gc.fillRect(half + i, 0, 1, height);
                gc.fillRect(half - i - 1, 0, 1, height);
            }

        if (exp.getLightBeam().getRadiationType() == RadiationType.RECTANGULAR)
            for (int i = 0; i < half; i++) {
                int wave = 0;
                double intensity = 0;

/*
                for (wave = exp.getLightBeam().getFirstWaveLength(); wave <= exp.getLightBeam().getSecondWaveLength(); wave++) {
                    intensity += intense.get(wave).get((int) (correction * i));
                }
                intensity /= exp.getLightBeam().getSecondWaveLength() - exp.getLightBeam().getFirstWaveLength() + 1;
*/
                for (wave = exp.getLightBeam().getFirstWaveLength(); wave <= exp.getLightBeam().getSecondWaveLength(); wave++) {
                    double correction = (intense.get(wave).size() * 2) / canvas.getWidth();
                    intensity = intense.get(wave).get((int) (correction * i));
                    gc.setFill(Wave.getRGB(wave, intensity));
                }
                gc.fillRect(half + i, 0, 1, height);
                gc.fillRect(half - i - 1, 0, 1, height);
            }
    }
}
