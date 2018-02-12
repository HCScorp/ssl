package hcs.dsl.ssl.backend.misc;

public class Interval<T extends Number> {

    public enum Type {
        Double,
        Integer
    }

    public T min;
    public T max;

    public final Type type;

    public Interval(Type type, T min, T max) {
        this.type = type;
        this.min = min;
        this.max = max;
    }
}
