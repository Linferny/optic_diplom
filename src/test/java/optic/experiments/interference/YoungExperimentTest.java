package optic.experiments.interference;

import lombok.val;
import optic.light.LightBeam;
import optic.light.RadiationType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.booleanThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class YoungExperimentTest {
    YoungExperiment experiment;

    @BeforeEach
    void before() {
        val beam = new LightBeam(RadiationType.MONOCHROMATIC, 600, 700);

        experiment = new YoungExperiment(beam);
        experiment.setD(0.000_001);
        experiment.setL(0.5);
        experiment.setScreenL(2);
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
        assertEquals(6, count);
    }

    @Test
    void testGetDelta() {
        experiment.setL(1.0);
        experiment.setD(1.0);
        assertEquals(1.0, experiment.getDelta(1.0));
    }

    @Test
    void testGetSetD() {
        experiment.setD(2.0);
        assertEquals(2.0, experiment.getD());
    }

    @Test
    void testGetSetL() {
        experiment.setL(2.0);
        assertEquals(2.0, experiment.getL());
    }
}