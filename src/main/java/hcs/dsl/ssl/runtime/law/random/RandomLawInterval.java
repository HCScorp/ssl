package hcs.dsl.ssl.runtime.law.random;

import hcs.dsl.ssl.runtime.law.Law;

public abstract class RandomLawInterval<T extends Number> extends Law<T> {

    protected final T min;
    protected final T max;

    protected RandomLawInterval(T min, T max) {
        this.min = min;
        this.max = max;
    }
}
