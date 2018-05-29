package optic.experiments.interference;

import lombok.*;
import lombok.experimental.FieldDefaults;
import optic.light.LightBeam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PROTECTED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterferenceExperiment {
    LightBeam lightBeam;
    double screenL;
    final double baseI = 0.25;

    public Map<Integer, List<Double>> getSpecter(int count) {
        Map<Integer, List<Double>> intensity = new HashMap<>();
        int[] waves = lightBeam.getWaves();

        for (int waveLength : waves) {
            intensity.put(waveLength, new ArrayList<>());

            final double waveL = waveLength * Math.pow(10, -9);
            final double k = 2 * Math.PI / waveL;

            for (double x = 0; x < screenL; x += screenL / count) {
                double I = baseI * (1 + Math.cos(k * getDelta(x, waveL)));

                intensity.get(waveLength).add(I);
            }
        }
        return intensity;
    }

    double getDelta(double... params) {
        return params.length == 0 ? 0 : params[0];
    }
}
