package optic.light;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LightBeam {
    @Getter
    RadiationType radiationType;
    @Getter
    int secondWaveLength;
    @Getter
    int firstWaveLength;

    public int[] getWaves() {
        if (radiationType == RadiationType.MONOCHROMATIC)
            return new int[]{firstWaveLength};
        if (radiationType == RadiationType.BICHROMATIC || radiationType == RadiationType.RECTANGULAR)
            return new int[]{firstWaveLength, secondWaveLength};
        return new int[0];
    }

    public void setRadiationType(RadiationType radiationType) {
        if (radiationType == RadiationType.MONOCHROMATIC) {
            secondWaveLength = firstWaveLength;
        }
        this.radiationType = radiationType;
    }

    public void setSecondWaveLength(int secondWaveLength) {
        if (secondWaveLength > Wave.MAX_WAVE_LENGTH) {
            this.secondWaveLength = Wave.MAX_WAVE_LENGTH;
            return;
        }
        if (secondWaveLength < firstWaveLength) {
            this.secondWaveLength = firstWaveLength;
            return;
        }

        this.secondWaveLength = secondWaveLength;
    }

    public void setFirstWaveLength(int firstWaveLength) {
        if (firstWaveLength < Wave.MIN_WAVE_LENGTH) {
            this.firstWaveLength = Wave.MIN_WAVE_LENGTH;
            return;
        }
        if (firstWaveLength > secondWaveLength) {
            this.firstWaveLength = secondWaveLength;
            return;
        }

        this.firstWaveLength = firstWaveLength;
    }

    public LightBeam() {
        this(RadiationType.MONOCHROMATIC, Wave.MAX_WAVE_LENGTH, Wave.MAX_WAVE_LENGTH);
    }

    public LightBeam(int waveLenght) {
        this(RadiationType.MONOCHROMATIC, waveLenght, waveLenght);
    }

    private LightBeam(RadiationType radiationType, int firstWaveLength, int secondWaveLength) {
        this.radiationType = radiationType;
        if (radiationType == RadiationType.MONOCHROMATIC && firstWaveLength != secondWaveLength) {
            secondWaveLength = firstWaveLength;
        }
        if (secondWaveLength < firstWaveLength){
            int temp = secondWaveLength;
            secondWaveLength = firstWaveLength;
            firstWaveLength = temp;
        }
        if (secondWaveLength > Wave.MAX_WAVE_LENGTH)
            secondWaveLength = Wave.MAX_WAVE_LENGTH;
        if (firstWaveLength < Wave.MIN_WAVE_LENGTH)
            firstWaveLength = Wave.MIN_WAVE_LENGTH;
        this.secondWaveLength = secondWaveLength;
        this.firstWaveLength = firstWaveLength;
    }
}
