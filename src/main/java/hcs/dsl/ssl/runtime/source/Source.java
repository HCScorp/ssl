package hcs.dsl.ssl.runtime.source;

import java.io.Serializable;

public abstract class Source<T extends Serializable> {
    public abstract T produceValue(long timestamp);
}