package runtime.law.markov;

import runtime.law.Law;

import java.io.Serializable;
import java.util.*;

public abstract class MarkovLaw<T extends Serializable> extends Law<T> {

    private final Map<T, TreeMap<Double, T>> edgeMap;
    private final Random rand;

    private T current;

    // TODO how to handle initial value ?????

    public MarkovLaw() {
        this.edgeMap = new HashMap<>();
        this.rand = new Random();
    }

    protected void addEdge(T value, Double proba, T target) {
        if (!edgeMap.containsKey(value)) {
            edgeMap.put(value, new TreeMap<>());
        }

        edgeMap.get(value).put(proba, target);
    }

    // TODO to test
    public T produceValue(long timestamp) {
        Double count = 0.0;
        TreeMap<Double, T> tMap = edgeMap.get(current);
        for (Map.Entry<Double, T> e : tMap.entrySet()) {
            count += e.getKey();
        }
        Double choice = (1 - rand.nextDouble()) * count;
        current = tMap.floorEntry(choice).getValue();
        return current;
    }
}
