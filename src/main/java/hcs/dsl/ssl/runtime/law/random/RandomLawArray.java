package hcs.dsl.ssl.runtime.law.random;

import hcs.dsl.ssl.runtime.law.Law;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public abstract class RandomLawArray<T extends Serializable> extends Law<T> {

    private final T[] array;
    private final int size;

    protected RandomLawArray(T... elements) {
        this.array = elements;
        this.size = elements.length;
    }


    @Override
    public T produceValue(long timestamp) {
        return array[ThreadLocalRandom.current().nextInt(size)];
    }
}
