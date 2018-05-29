package optic.light;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LightBeamTest {

    @Test
    void testGetSetWaveLengths() {
        int f = 500;
        int s = 600;
        val beam = new LightBeam(RadiationType.BICHROMATIC, 380, 780);
        assertEquals(380, beam.getFirstWaveLength());
        assertEquals(780, beam.getSecondWaveLength());
        beam.setFirstWaveLength(f);
        beam.setSecondWaveLength(s);
        assertEquals(f, beam.getFirstWaveLength());
        assertEquals(s, beam.getSecondWaveLength());
    }

    @Test
    void testGetWaves() {
        val beam = new LightBeam(RadiationType.BICHROMATIC, 380, 780);
        val waves = beam.getWaves();
        assertEquals(380, waves[0]);
        assertEquals(780, waves[1]);
        beam.setRadiationType(RadiationType.MONOCHROMATIC);
        assertEquals(380, beam.getWaves()[0]);
        beam.setRadiationType(RadiationType.SPECTER);
        val specterWaves = beam.getWaves();
        int counter = 380;
        for (int i = 0; i < specterWaves.length; i++) {
            assertEquals(counter, specterWaves[i]);
            counter++;
        }
    }

    @Test
    void testSetGetRadiationType() {
        val beam = new LightBeam();
        beam.setRadiationType(RadiationType.MONOCHROMATIC);
        assertEquals(RadiationType.MONOCHROMATIC, beam.getRadiationType());
        beam.setRadiationType(RadiationType.BICHROMATIC);
        assertEquals(RadiationType.BICHROMATIC, beam.getRadiationType());
        beam.setRadiationType(RadiationType.SPECTER);
        assertEquals(RadiationType.SPECTER, beam.getRadiationType());
    }

    @Test
    void testGetSetSecondWaveLength() {
        val beam = new LightBeam(RadiationType.BICHROMATIC, 380, 780);
        beam.setSecondWaveLength(500);
        assertEquals(500, beam.getSecondWaveLength());
    }

    @Test
    void testGetSetFirstWaveLength() {
        val beam = new LightBeam(RadiationType.BICHROMATIC, 380, 780);
        beam.setFirstWaveLength(500);
        assertEquals(500, beam.getFirstWaveLength());
    }
}
