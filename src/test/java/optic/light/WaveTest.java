package optic.light;

import javafx.scene.paint.Color;
import lombok.val;
import optic.light.Wave;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class WaveTest {

    @Test
    void testGetWaveColor(){
        int waveLength = 500;
        val color = Wave.getRGB(waveLength);
        val _color = new Color(0, 1.0, 146/255.0, 1.0);
        assertTrue(Math.abs(_color.getRed() - color.getRed()) < 0.1);
        assertTrue(Math.abs(_color.getGreen() - color.getGreen()) < 0.1);
        assertTrue(Math.abs(_color.getBlue() - color.getBlue()) < 0.1);
    }
}
