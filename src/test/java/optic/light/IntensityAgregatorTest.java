package optic.light;

import javafx.scene.paint.Color;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntensityAgregatorTest {

    @Test
    void agregate() {
        val map = new HashMap<Integer, List<Double>>();
        map.put(380, List.of(0.5, 0.2, 0.1));
        val res = IntensityAgregator.agregate(map);
        val intense = new double[]{1.0, 0.4, 0.2};
        Color[] colors = new Color[]{
                new Color(1.0, 0.0, 1.0, 1.0),
                new Color(0.1608, 0.0, 0.1608, 1.0),
                new Color(0.0392, 0.0, 0.0392, 1.0)
        };
        for (int i = 0; i < res.getIntensity().length; i++) {
            assertEquals(intense[i], res.getIntensity()[i]);
            assertTrue(0.001 > Math.abs(res.getColors()[i].getRed() - colors[i].getRed()));
            assertTrue(0.001 > Math.abs(res.getColors()[i].getGreen() - colors[i].getGreen()));
            assertTrue(0.001 > Math.abs(res.getColors()[i].getBlue() - colors[i].getBlue()));
        }
    }
}