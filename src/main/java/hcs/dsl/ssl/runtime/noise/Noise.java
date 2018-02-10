package hcs.dsl.ssl.runtime.noise;


public abstract class Noise<T extends Number> {

    protected final T min;
    protected final T max;

    protected Noise(T min, T max) {
        if (min.doubleValue() > max.doubleValue()) {
            throw new IllegalArgumentException("minimum value (" + min + ") for noise interval must be inferior to the maximum (" + max + ")");
        }
        this.min = min;
        this.max = max;
    }

    public abstract T apply(T val);
}
