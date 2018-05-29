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
import java.util.function.Function;

// Дано: расстояние между источником и экраном, расстояние между щелями и его дельта, количество штрихов и их общее растояние = дельтаХ,

@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewtonExperiment extends InterferenceExperiment {
    @Getter
    @Setter
    double R; // радиус кривизны

    public NewtonExperiment() {
        lightBeam = new LightBeam();
    }

    public NewtonExperiment(LightBeam lightBeam) {
        this.lightBeam = lightBeam;
    }

    @Override
    public Map<Integer, List<Double>> getSpecter(int count) {
        return super.getSpecter(count);
    }

    @Override
    double getDelta(double... params) {
        return params[0] * params[0] / R + params[1] / 2;
    }
}
