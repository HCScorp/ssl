package hcs.dsl.ssl.backend.misc;

public class Interval<T extends Number> {

    public enum Type {
        Double,
        Integer
    }

    public T min;
    public T max;

    public Interval(T min, T max) {
        this.min = min;
        this.max = max;
    }
}
