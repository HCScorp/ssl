package runtime.law.random;

import java.util.concurrent.ThreadLocalRandom;

public abstract class RandomLawIntervalDouble extends RandomLawInterval<Double> {

    protected RandomLawIntervalDouble(Double min, Double max) {
        super(min, max);
    }

    @Override
    public Double produceValue(long timestamp) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
}
