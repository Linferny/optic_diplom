package optic.experiments.interference;

import lombok.val;
import optic.light.LightBeam;
import optic.light.RadiationType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InterferenceExperimentTest {
    static InterferenceExperiment experiment;

    @BeforeAll
    static void beforeAll() {
        val beamMock = mock(LightBeam.class);
        when(beamMock.getRadiationType()).thenReturn(RadiationType.MONOCHROMATIC);
        when(beamMock.getWaves()).thenReturn(new int[]{380});

        experiment = new InterferenceExperiment(beamMock, 2000);
    }

    @Test
    void testGetSpecter() {
        assertEquals(new Double(0.5), experiment.getSpecter(1).get(380).get(0));
    }

    @Test
    void testGetDelta() {
        assertEquals(0, experiment.getDelta(0));
    }

    @Test
    void testGetSetLightBeam() {
        val beam = new LightBeam(RadiationType.BICHROMATIC, 380, 780);
        experiment.setLightBeam(beam);
        assertEquals(beam, experiment.getLightBeam());
    }

    @Test
    void testGetSetScreenL() {
        experiment.setScreenL(2000);
        assertEquals(2000.0, experiment.getScreenL());
    }

    @Test
    void testGetBaseI() {
        assertEquals(0.25, experiment.getBaseI());
    }
}