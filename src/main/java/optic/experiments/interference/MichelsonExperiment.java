package optic.experiments.interference;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import optic.light.LightBeam;
import optic.light.RadiationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MichelsonExperiment extends InterferenceExperiment{
    @Getter
    @Setter
    double angleDegrees; // угол отклонения зеркал от перпендикулярности

    public MichelsonExperiment() {
        super();
        lightBeam = new LightBeam();
    }

    public MichelsonExperiment(LightBeam lightBeam) {
        super();
        this.lightBeam = lightBeam;
    }

    @Override
    public Map<Integer, List<Double>> getSpecter(int count) {
        return super.getSpecter(count);
    }

    @Override
    double getDelta(double... params) {
        return 2 * params[0] * Math.tan(angleDegrees);
    }

}
