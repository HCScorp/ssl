package runtime.law.random;

import runtime.law.Law;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
