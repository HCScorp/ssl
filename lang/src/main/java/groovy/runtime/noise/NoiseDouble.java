package runtime.noise;

import java.util.concurrent.ThreadLocalRandom;

public class NoiseDouble extends Noise<Double> {

    public NoiseDouble(Double min, Double max) {
        super(min, max);
    }

    @Override
    public Double apply(Double val) {
        return val + ThreadLocalRandom.current().nextDouble(min, max);
    }
}
