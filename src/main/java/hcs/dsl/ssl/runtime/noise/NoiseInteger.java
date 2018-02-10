package hcs.dsl.ssl.runtime.noise;

import java.util.concurrent.ThreadLocalRandom;

public class NoiseInteger extends Noise<Integer> {

    public NoiseInteger(Integer min, Integer max) {
        super(min, max);
    }

    @Override
    public Integer apply(Integer val) {
        return val + ThreadLocalRandom.current().nextInt(min, max);
    }
}
