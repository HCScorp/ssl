package runtime.law.random;

import java.util.concurrent.ThreadLocalRandom;

public abstract class RandomLawIntervalInteger extends RandomLawInterval<Integer> {

    protected RandomLawIntervalInteger(Integer min, Integer max) {
        super(min, max);
    }

    @Override
    public Integer produceValue(long timestamp) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
