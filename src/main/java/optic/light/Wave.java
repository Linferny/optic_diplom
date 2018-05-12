package optic.light;

import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Wave {
    static Color[] waveLengthToRGB;

    public final static int MAX_WAVE_LENGTH = 780;
    public final static int MIN_WAVE_LENGTH = 380;

    /**
     * Array of Wave Length to RGB initialization and filling
     */
    static {
        List<Color> colors = new ArrayList<>();
        Color color;
        double factor = 0.0;
        double gamma = 0.8;

        for (int waveLength = 380; waveLength <= 780; waveLength++) {
            if (waveLength < 440) {
                color = new Color(
                        -(waveLength - 440) / 60.0,
                        0.0,
                        1.0,
                        1.0);
            } else if (waveLength < 490) {
                color = new Color(
                        0.0,
                        (waveLength - 440) / 50.0,
                        1.0,
                        1.0
                );
            } else if (waveLength < 510) {
                color = new Color(
                        0.0,
                        1.0,
                        -(waveLength - 510) / 20.0,
                        1.0
                );
            } else if (waveLength < 580) {
                color = new Color(
                        (waveLength - 510) / 70.0,
                        1.0,
                        0.0,
                        1.0
                );
            } else if (waveLength < 645) {
                color = new Color(
                        1.0,
                        -(waveLength - 645) / 65.0,
                        0.0,
                        1.0
                );
            } else if (waveLength <= 780) {
                color = new Color(
                        1.0,
                        0.0,
                        0.0,
                        1.0
                );
            } else {
                color = new Color(
                        0.0,
                        0.0,
                        0.0,
                        1.0
                );
            }

            if (waveLength < 420) {
                factor = 0.3 + 0.7 * (waveLength - 380) / 40;
            } else if (waveLength <= 700) {
                factor = 1.0;
            } else if (waveLength <= 780) {
                factor = 0.3 + 0.7 * (780 - waveLength) / 80;
            } else {
                factor = 0.0;
            }

            colors.add(new Color(
                    adjust(color.getRed(), factor, gamma),
                    adjust(color.getGreen(), factor, gamma),
                    adjust(color.getBlue(), factor, gamma),
                    1.0
            ));
        }

        waveLengthToRGB = new Color[780 - 380 + 1];
        for (int i = 0; i < waveLengthToRGB.length; i++) {
            waveLengthToRGB[i] = colors.get(i);
        }
    }

    public static Color getRGB(int waveLength) {
        if (waveLength < MIN_WAVE_LENGTH || waveLength > waveLengthToRGB.length + MIN_WAVE_LENGTH)
            return new Color(
                    0.0,
                    0.0,
                    0.0,
                    1.0
            );
        return waveLengthToRGB[waveLength - MIN_WAVE_LENGTH];
    }

    private static double adjust(double color, double factor, double gamma) {
        return Math.pow(color * factor, gamma);
    }
}
