package hcs.dsl.ssl.model.law;

import hcs.dsl.ssl.model.Namable;
import hcs.dsl.ssl.model.misc.VarType;

public abstract class Law implements Namable {

    public enum Type {
        RANDOM,
        MARKOV,
        FUNCTION,
        FILE
    }

    private final String name;
    private final Type type;

    public Law(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public abstract VarType getValType();
}
