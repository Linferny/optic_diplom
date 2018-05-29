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
        if (radiationType == RadiationType.BICHROMATIC)
            return new int[]{firstWaveLength, secondWaveLength};
        if (radiationType == RadiationType.SPECTER) {
            int[] waves = new int[secondWaveLength - firstWaveLength + 1];
            for (int i = 0; i < waves.length; i++) {
                waves[i] = firstWaveLength + i;
            }
            return waves;
        }
        return new int[0];
    }

    public void setRadiationType(RadiationType radiationType) {
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

    public LightBeam(RadiationType radiationType, int firstWaveLength, int secondWaveLength) {
        this.radiationType = radiationType;
        if (secondWaveLength < firstWaveLength) {
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
