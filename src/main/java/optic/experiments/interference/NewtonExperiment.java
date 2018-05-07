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
public class NewtonExperiment {
    @Getter
    @Setter
    double R; // радиус кривизны
    @Getter
    @Setter
    LightBeam lightBeam;
    @Getter
    @Setter
    double screenL = 2.0;

    final double baseI = 0.25;

    int pointsCount = 10_000;

    public NewtonExperiment() {
        lightBeam = new LightBeam();
    }

    public NewtonExperiment(LightBeam lightBeam) {
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
        if (lightBeam.getRadiationType() == RadiationType.RECTANGULAR) {
            waves = new int[lightBeam.getSecondWaveLength() - lightBeam.getFirstWaveLength() + 1];
            for (int i = 0; i < waves.length; i++) {
                waves[i] = i + lightBeam.getFirstWaveLength();
            }
        }

        for (int waveLength : waves) {
            intensity.put(waveLength, new ArrayList<>());

            final double waveL = waveLength * Math.pow(10, -9);
            final double k = 2 * Math.PI / waveL;

            for (double r = 0; r < screenL; r += screenL / count) {
                double I = baseI * (1 + Math.cos(k * getDelta(r, waveL)));

                intensity.get(waveLength).add(I);
            }
        }
        return intensity;
    }

    double getDelta(double r, double waveLength) {
        return r * r / R + waveLength / 2;
    }
}
