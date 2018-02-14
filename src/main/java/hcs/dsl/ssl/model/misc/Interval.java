package hcs.dsl.ssl.model.misc;

public class Interval<T extends Number> {

    public enum Type {
        Double,
        Integer
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return min +","+max;
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
