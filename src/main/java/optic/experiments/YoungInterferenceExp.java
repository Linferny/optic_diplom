package optic.experiments;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import optic.light.LightBeam;
import optic.light.RadiationType;

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

    int pointsCount = 10_000;

    public YoungInterferenceExp() {
        lightBeam = new LightBeam();
    }

    public YoungInterferenceExp(LightBeam lightBeam) {
        this.lightBeam = lightBeam;
    }

    public List<Double> getBichrom(int count) {
        List<Double> intensity = new ArrayList<>();

        final double baseI = 0.25;
        final double screenL = 0.04;

        final double waveL1 = lightBeam.getFirstWaveLength() * Math.pow(10, -9);
        final double waveL2 = lightBeam.getSecondWaveLength() * Math.pow(10, -9);
        final double k1 = 2 * Math.PI / waveL1;
        final double k2 = 2 * Math.PI / waveL2;

        final double deltaK = (k1 - k2) / 2;
        final double sumK = (k1 + k2) / 2;

        for (double x = 0; x < screenL; x += screenL / count) {
            double I = 2 * baseI * (1 + Math.cos(sumK * getDelta(x)) * Math.cos(deltaK * getDelta(x)));

            intensity.add(I);
        }

        return intensity;
    }

    public Map<Integer, List<Double>> getBichrom2(int count) {
        Map<Integer, List<Double>> intensity = new HashMap<>();

        int w1 = lightBeam.getFirstWaveLength();
        int w2 = lightBeam.getSecondWaveLength();

        intensity.put(w1, new ArrayList<>());
        intensity.put(w2, new ArrayList<>());

        final double baseI = 0.25;
        final double screenL = 2.0;

        final double waveL1 = lightBeam.getFirstWaveLength() * Math.pow(10, -9);
        final double waveL2 = lightBeam.getSecondWaveLength() * Math.pow(10, -9);
        final double k1 = 2 * Math.PI / waveL1;
        final double k2 = 2 * Math.PI / waveL2;

        for (double x = 0; x < screenL; x += screenL / count) {
            double I1 = baseI * (1 + Math.cos(k1 * getDelta(x)));
            double I2 = baseI * (1 + Math.cos(k2 * getDelta(x)));

            intensity.get(w1).add(I1);
            intensity.get(w2).add(I2);
        }

        return intensity;
    }



    public Map<Integer, List<Double>> getSpector() {
        Map<Integer, List<Double>> intensity = new HashMap<>();

        final double baseIntensivity = 0.25;
        final double screenL = 0.1;

        // delta x = L / d * lambda0
        if (lightBeam.getRadiationType() == RadiationType.MONOCHROMATIC) {
            intensity.put(lightBeam.getFirstWaveLength(), new ArrayList<>());

            final double waveL = lightBeam.getFirstWaveLength() * Math.pow(10, -9);
            final double k = 2 * Math.PI / waveL;
            final double deltaX = L / d * waveL;
            final int extremumCount = (int) (screenL / deltaX);
            final double screenScale = screenL / extremumCount;

            for (double extremum = 0; extremum <= screenL / 2; extremum += screenScale / pointsCount) {
                intensity.get(lightBeam.getFirstWaveLength()).add(2 * baseIntensivity * (1 + Math.cos(k * getDelta(extremum))));
            }
        }

        if (lightBeam.getRadiationType() == RadiationType.BICHROMATIC) {
            /*
            intensity.put(lightBeam.getFirstWaveLength(), new ArrayList<>());
            intensity.put(lightBeam.getSecondWaveLength(), new ArrayList<>());

            final double waveL1 = lightBeam.getFirstWaveLength() * Math.pow(10, -9);
            final double waveL2 = lightBeam.getSecondWaveLength() * Math.pow(10, -9);
            final double k1 = 2 * Math.PI / waveL1;
            final double k2 = 2 * Math.PI / waveL2;

            Map<Integer, Double> max = new HashMap<>();
            max.put(lightBeam.getFirstWaveLength(), -1.0);
            max.put(lightBeam.getSecondWaveLength(), -1.0);

            for (double x = 0; x <= screenL / 2; x += screenL / pointsCount) {
                double i1 = 2 * baseIntensivity * (1 + Math.cos(k1 * getDelta(x)));
                double i2 = 2 * baseIntensivity * (1 + Math.cos(k2 * getDelta(x)));
                if (i1 > max.get(lightBeam.getFirstWaveLength()))
                    max.put(lightBeam.getFirstWaveLength(), i1);
                if (i1 > max.get(lightBeam.getSecondWaveLength()))
                    max.put(lightBeam.getSecondWaveLength(), i2);
                intensity.get(lightBeam.getFirstWaveLength())
                        .add(i1);
                intensity.get(lightBeam.getSecondWaveLength())
                        .add(i2);
            }

            for (int k: intensity.keySet()) {
                for (int i = 0; i < intensity.get(k).size(); i++) {
                    intensity.get(k).set(i, intensity.get(k).get(i) / max.get(k));
                }
            }
            */
        }

        if (lightBeam.getRadiationType() == RadiationType.RECTANGULAR) {
            for (int waveLength = lightBeam.getFirstWaveLength(); waveLength <= lightBeam.getSecondWaveLength(); waveLength++) {
                intensity.put(waveLength, new ArrayList<>());

                final double waveL = waveLength * Math.pow(10, -9);
                final double k = 2 * Math.PI / waveL;
                final double deltaX = L / d * waveL;
                final int extremumCount = (int) (screenL / deltaX);
                final double screenScale = screenL / extremumCount;

                for (double extremum = 0;
                     extremum <= screenL / 2;
                     extremum += screenScale / pointsCount) {

                    intensity.get(waveLength)
                            .add(2 * baseIntensivity * (1 + Math.cos(k * getDelta(extremum))));
                }
            }
        }

        return intensity;
    }

    double getDelta(double x) {
        return x * d / L;
    }

    @Override
    public void run() {

    }
}
