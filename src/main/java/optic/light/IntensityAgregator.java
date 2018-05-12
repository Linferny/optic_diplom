package optic.light;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IntensityAgregator {
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
