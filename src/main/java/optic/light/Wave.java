package optic.light;

import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Wave {
    static int offset;
    static Color[] waveLengthToRGB;

    public final static int MAX_WAVE_LENGTH = 780;
    public final static int MIN_WAVE_LENGTH = 380;

    /**
     * Array of wave length to RGB initialization and filling
     */
    static {
        offset = 380;
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

    /**
     * Get Color from wave length
     *
     * @param waveLength
     * @return
     */
    public static Color getRGB(int waveLength) {
        if (waveLength < offset || waveLength > waveLengthToRGB.length + offset)
            return new Color(
                    0.0,
                    0.0,
                    0.0,
                    1.0
            );
        return waveLengthToRGB[waveLength - offset];
    }

    public static Color getRGB(int waveLength, double opacity) {
        Color color = getRGB(waveLength);
        return new Color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                opacity >= 0 && opacity <= 1.0 ? opacity : 1.0
        );
    }

    /**
     * Get Color from wave frequency
     *
     * @param frequency
     * @return
     */
    public static Color getRGB(double frequency) {
        return getRGB(freqToLength(frequency));
    }

    private static int freqToLength(double frequency) {
        double lightSpeed = 299_792_458;
        return (int) (lightSpeed * frequency);
    }

    private static double adjust(double color, double factor, double gamma) {
        return Math.pow(color * factor, gamma);
    }
}
