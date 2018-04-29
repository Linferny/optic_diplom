package optic.light;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LightBeam {
    @Getter
    boolean monochromatic;  // CHANGE TO ENUM
    @Getter
    int maxWaveLenght;
    @Getter
    int minWaveLength;

    public int[] getWaves() {
        if (monochromatic)
            return new int[]{minWaveLength};
        int[] waves = new int[maxWaveLenght - minWaveLength + 1];
        for (int i = 0; i < waves.length; i++) {
            waves[i] = i + minWaveLength;
        }
        return waves;
    }

    public void setMonochromatic(boolean monochromatic) {
        this.monochromatic = monochromatic;
        if (monochromatic)
            maxWaveLenght = minWaveLength;
    }

    public void setMaxWaveLenght(int maxWaveLenght) {
        if (maxWaveLenght > Wave.MAX_WAVE_LENGTH) {
            this.maxWaveLenght = Wave.MAX_WAVE_LENGTH;
            return;
        }
        if (maxWaveLenght < minWaveLength) {
            this.maxWaveLenght = minWaveLength;
            return;
        }

        this.maxWaveLenght = maxWaveLenght;
    }

    public void setMinWaveLength(int minWaveLength) {
        if (minWaveLength < Wave.MIN_WAVE_LENGTH) {
            this.minWaveLength = Wave.MIN_WAVE_LENGTH;
            return;
        }
        if (minWaveLength > maxWaveLenght) {
            this.minWaveLength = maxWaveLenght;
            return;
        }

        this.minWaveLength = minWaveLength;
    }

    public LightBeam() {
        this(true, Wave.MAX_WAVE_LENGTH, Wave.MAX_WAVE_LENGTH);
    }

    public LightBeam(int waveLenght) {
        this(true, waveLenght, waveLenght);
    }

    public LightBeam(int minWaveLength, int maxWaveLength) {
        this(false, minWaveLength, maxWaveLength);
    }

    private LightBeam(boolean monochromatic, int maxWaveLenght, int minWaveLength) {
        this.monochromatic = monochromatic;
        this.maxWaveLenght = maxWaveLenght;
        this.minWaveLength = minWaveLength;
    }
}
