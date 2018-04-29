package optic.experiments;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import optic.light.LightBeam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Дано: расстояние между источником и экраном, расстояние между щелями и его дельта, количество штрихов и их общее растояние = дельтаХ,

@FieldDefaults(level = AccessLevel.PRIVATE)
public class YoungInterferenceExp implements Runnable {
    @Getter
    @Setter
    double d; // расстояние между отверстиями
    @Getter
    @Setter
    double L; // расстояние от экрана до источников
    @Getter
    @Setter
    double angle; // угол между центральной линией и лучом
    @Getter
    @Setter
    LightBeam lightBeam;

    public YoungInterferenceExp() {
        lightBeam = new LightBeam();
    }

    public YoungInterferenceExp(LightBeam lightBeam) {
        this.lightBeam = lightBeam;
    }

    public double[] getSpector() {
        Map<Integer, List<Double>> results = new HashMap<>();

        final double baseIntensivity = 0.25;

        for (int waveLength : lightBeam.getWaves()) {
            results.put(waveLength, new ArrayList<>());
            final double waveL = waveLength * Math.pow(10, -9);
            final double k = 2 * Math.PI / waveL;
            final double lengthBetweenMax = L / d * waveL;


        }

        return new double[1];
    }

    public List<Double> getLength() {
        double length = 720 * Math.pow(10, -9);

        d = 0.001;

        L = 0.002;

        double intensiveBase = 0.25;

        double k = 2 * Math.PI / length;

        int scale = 50;

        List<Double> intense = new ArrayList<>();

        // lenght * 6 = fori -> length ~ 50
        for (int i = -scale * 3; i <= scale * 3; i++) {
            intense.add(2 * intensiveBase * (1 + Math.cos(k * getDelta(i * length / 50))));
        }

        return intense;
    }

    double getDelta(double x) {
        return Math.abs(x * d / L);
        //return Math.abs(getR1(x) - getR2(x));
    }

    double getR1(double x) {
        x = Math.abs(x);
        return Math.sqrt(L * L + (x - d / 2) * (x - d / 2));
    }

    double getR2(double x) {
        x = Math.abs(x);
        return Math.sqrt(L * L + (x + d / 2) * (x + d / 2));
    }

    @Override
    public void run() {

    }
}
