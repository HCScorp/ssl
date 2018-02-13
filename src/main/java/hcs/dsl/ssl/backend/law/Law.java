package hcs.dsl.ssl.backend.law;

import hcs.dsl.ssl.backend.misc.Var;

public abstract class Law {

    public enum Type {
        RANDOM,
        MARKOV,
        FUNCTION
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

    public abstract Var.Type getValType();
}
