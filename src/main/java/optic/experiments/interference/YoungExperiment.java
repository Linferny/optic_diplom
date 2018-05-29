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

// Дано: расстояние между источником и экраном, расстояние между щелями и его дельта, количество штрихов и их общее растояние = дельтаХ,

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YoungExperiment extends InterferenceExperiment {
    double d; // расстояние между отверстиями
    double L; // расстояние от экрана до источников

    public YoungExperiment() {
        super();
        lightBeam = new LightBeam();
    }

    public YoungExperiment(LightBeam lightBeam) {
        super();
        this.lightBeam = lightBeam;
    }

    @Override
    public Map<Integer, List<Double>> getSpecter(int count) {
        return super.getSpecter(count);
    }

    @Override
    protected double getDelta(double... params) {
        return params[0] * d / L;
    }
}
