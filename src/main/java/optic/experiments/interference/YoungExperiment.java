package optic.experiments.interference;

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
public class YoungExperiment {
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
    @Getter
    @Setter
    double screenL = 2.0;

    final double baseI = 0.25;

    int pointsCount = 10_000;

    public YoungExperiment() {
        lightBeam = new LightBeam();
    }

    public YoungExperiment(LightBeam lightBeam) {
        this.lightBeam = lightBeam;
    }

    public Map<Integer, List<Double>> getSpecter(int count) {
        Map<Integer, List<Double>> intensity = new HashMap<>();
        int[] waves = new int[0];

        if (lightBeam.getRadiationType() == RadiationType.MONOCHROMATIC)
            waves = new int[]{lightBeam.getFirstWaveLength()};
        if (lightBeam.getRadiationType() == RadiationType.BICHROMATIC)
            waves = new int[]{lightBeam.getFirstWaveLength(),
                    lightBeam.getSecondWaveLength()};
        if (lightBeam.getRadiationType() == RadiationType.SPECTER) {
            waves = new int[lightBeam.getSecondWaveLength() - lightBeam.getFirstWaveLength() + 1];
            for (int i = 0; i < waves.length; i++) {
                waves[i] = i + lightBeam.getFirstWaveLength();
            }
        }

        for (int waveLength : waves) {
            intensity.put(waveLength, new ArrayList<>());

            final double waveL1 = waveLength * Math.pow(10, -9);
            final double k1 = 2 * Math.PI / waveL1;

            for (double x = 0; x < screenL; x += screenL / count) {
                double I1 = baseI * (1 + Math.cos(k1 * getDelta(x)));

                intensity.get(waveLength).add(I1);
            }
        }
        return intensity;
    }

    double getDelta(double x) {
        return x * d / L;
    }
}
