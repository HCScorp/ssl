package hcs.dsl.ssl.model.misc;

public class Interval<T extends Number> {

    public enum Type {
        Double,
        Integer
    }
    private T min;
    private T max;

    public final Type type;

    public Interval(Type type, T min, T max) {
        this.type = type;
        this.min = min;
        this.max = max;
    }

    public Type getType() {
        return type;
    }

    public VarType getValType() {
        return VarType.valueOf(type.name());
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    @Override
    public String toString() {
        return min + "," + max;
    }
}
