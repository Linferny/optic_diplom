package optic.light;

import javafx.scene.paint.Color;
import optic.experiments.AgregatedResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntensityAgregator {
    private static Color[] agregate2(Map<Integer, List<Double>> intensity) {
        double max = -1;
        int size = intensity.get(intensity.keySet().iterator().next()).size();

        Map<Integer, List<Color>> colors = new HashMap<>();

        List<RGBColor> normalizedColors = new ArrayList<>(size);
        Color[] result = new Color[size];
        double[] weight = new double[size];

        // Преобразовали в цвета с учетом интенсивности
        for (Integer waveLength : intensity.keySet()) {
            colors.put(waveLength, new ArrayList<>());

            for (int i = 0; i < size; i++) {
                double I = intensity.get(waveLength).get(i);
                weight[i] += I;
                colors.get(waveLength)
                        .add(
                                Wave.getColorWithIntensity(
                                        waveLength,
                                        I)
                        );

            }
        }

        for (int i = 0; i < size; i++) {
            RGBColor rgb = new RGBColor(0.0, 0.0, 0.0, 1.0);

            for (Integer waveLength : intensity.keySet()) {
                double I = intensity.get(waveLength).get(i);
                Color c = colors.get(waveLength).get(i);
                rgb.red += c.getRed();
                rgb.green += c.getGreen();
                rgb.blue += c.getBlue();
            }

            if (rgb.red > max)
                max = rgb.red;
            if (rgb.green > max)
                max = rgb.green;
            if (rgb.blue > max)
                max = rgb.blue;

            normalizedColors.add(rgb);
        }

        for (int i = 0; i < size; i++) {
            RGBColor rgb = normalizedColors.get(i);
            result[i] = new Color(
                    rgb.red / max,
                    rgb.green / max,
                    rgb.blue / max,
                    1.0);
        }

        return result;
    }

    public static AgregatedResult agregate(Map<Integer, List<Double>> data) {
        int size = data.get(data.keySet().iterator().next()).size();

        List<RGBColor> normalizedColors = new ArrayList<>(size);
        Color[] colors = new Color[size];
        double[] intensity = new double[size];
        double maxIntense = -1;
        double maxRGBColor = -1;

        for (int i = 0; i < size; i++) {
            RGBColor rgb = new RGBColor(0.0, 0.0, 0.0, 1.0);

            for (Integer waveLength : data.keySet()) {
                double I = data.get(waveLength).get(i);
                Color c = Wave.getRGB(waveLength);

                intensity[i] += I;

                rgb.red += c.getRed() * I;
                rgb.green += c.getGreen() * I;
                rgb.blue += c.getBlue() * I;
            }

            if (rgb.red > maxRGBColor)
                maxRGBColor = rgb.red;
            if (rgb.green > maxRGBColor)
                maxRGBColor = rgb.green;
            if (rgb.blue > maxRGBColor)
                maxRGBColor = rgb.blue;

            if (intensity[i] > maxIntense)
                maxIntense = intensity[i];

            normalizedColors.add(rgb);
        }

        for (int i = 0; i < size; i++) {
            intensity[i] /= maxIntense;
            RGBColor rgb = normalizedColors.get(i);

            colors[i] = new Color(
                    rgb.red / maxRGBColor * intensity[i],
                    rgb.green / maxRGBColor * intensity[i],
                    rgb.blue / maxRGBColor * intensity[i],
                    1.0);
        }

        return new AgregatedResult(colors, intensity);
    }
}
