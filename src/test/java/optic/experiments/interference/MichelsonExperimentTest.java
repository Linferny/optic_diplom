package optic.experiments.interference;

import lombok.val;
import optic.light.LightBeam;
import optic.light.RadiationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MichelsonExperimentTest {
    MichelsonExperiment experiment;

    @BeforeEach
    void before() {
        val beam = new LightBeam(RadiationType.MONOCHROMATIC, 600, 700);

        experiment = new MichelsonExperiment(beam);
        experiment.setAngleDegrees(2 / 3600.0);
        experiment.setScreenL(0.002);
    }

    @Test
    void testGetSpecter() {
        val realRes = experiment.getSpecter(200);
        int count = 0;
        double prev = Double.MAX_VALUE;
        boolean fall = true;

        for (Double d : realRes.get(600)) {
            if (fall && d > prev) {
                fall = false;
            } else if (!fall && d < prev) {
                count++;
                fall = true;
            }
            prev = d;
        }
        assertEquals(3, count);
    }

    @Test
    void testGetDelta() {
        assertTrue(0.000_1 > Math.abs(0.001_111 - experiment.getDelta(1.0)));
    }

    @Test
    void testGetSetAngleDegrees() {
        experiment.setAngleDegrees(2.0);
        assertEquals(2.0, experiment.getAngleDegrees());
    }
}