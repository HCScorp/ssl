package hcs.dsl.ssl.runtime.law.markov;

import hcs.dsl.ssl.runtime.law.Law;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public abstract class MarkovLaw<T extends Serializable> extends Law<T> {

    private final Map<T, TreeMap<T, Double>> edgeMap;

    private T current;

    public MarkovLaw() {
        this.edgeMap = new HashMap<>();
        // todo verify the model
    }

    protected void addEdge(T value, Double proba, T target) {
        if (edgeMap.isEmpty()) {
            this.current = value;
        }

        if (!edgeMap.containsKey(value)) {
            edgeMap.put(value, new TreeMap<>());
        }

        edgeMap.get(value).put(target, proba);
    }

    public T produceValue(long timestamp) {
        TreeMap<T, Double> tMap = edgeMap.get(current);
        Double totalWeight = tMap.entrySet().stream().mapToDouble(Map.Entry::getValue).sum();

        if (totalWeight != 1.0) {
            throw new IllegalArgumentException("total weight is " + totalWeight + " but must be between 1.0 (for value: " + current + ")");
        }

        Double value = ThreadLocalRandom.current().nextDouble() * totalWeight;
        Double weight = 0.0;
        for (Map.Entry<T, Double> e : tMap.entrySet()) {
            weight += e.getValue();
            if (value < weight) {
                current = e.getKey();
                return current;
            }
        }

        throw new IllegalStateException("no state to go for current value: " + current);
    }
}
