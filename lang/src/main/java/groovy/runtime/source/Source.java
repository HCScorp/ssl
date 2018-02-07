package runtime.source;

import runtime.law.Law;

import java.io.Serializable;

public abstract class Source<T extends Serializable> extends Law<T> {


    public abstract void init();

    // TODO
}