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

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MichelsonExperiment {
    final static double DEGREES_TO_RADIANS_CONST = 0.0174533;
    @Getter
    @Setter
    double angleDegrees; // угол отклонения зеркал от перпендикулярности
    @Getter
    @Setter
    LightBeam lightBeam;
    @Getter
    @Setter
    double screenL = 2.0;

    final double baseI = 0.25;

    public MichelsonExperiment() {
        lightBeam = new LightBeam();
    }

    public MichelsonExperiment(LightBeam lightBeam) {
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

            final double waveL = waveLength * Math.pow(10, -9);
            final double k = 2 * Math.PI / waveL;

            for (double x = 0; x < screenL / 2; x += screenL / 2 / count) {
                double I = baseI * (1 + Math.cos(k * getDelta(x)));

                intensity.get(waveLength).add(I);
            }
        }
        return intensity;
    }

    double getDelta(double x) {
        return 2 * x * Math.tan(angleDegrees);
    }

}
