package optic.experiments.interference;

import lombok.val;
import optic.light.LightBeam;
import optic.light.RadiationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewtonExperimentTest {
    NewtonExperiment experiment;

    @BeforeEach
    void before() {
        val beam = new LightBeam(RadiationType.MONOCHROMATIC, 600, 700);

        experiment = new NewtonExperiment(beam);
        experiment.setR(0.03);
        experiment.setScreenL(0.000_2);
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
        assertEquals(2, count);
    }

    @Test
    void testGetDelta() {
        assertEquals(1.03, experiment.getDelta(0.03, 2));
    }

    @Test
    void testGetSetR() {
        experiment.setR(1.0);
        assertEquals(1.0, experiment.getR());
    }
}