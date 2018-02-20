package hcs.dsl.ssl.model.law.markov;

import hcs.dsl.ssl.model.law.Law;
import hcs.dsl.ssl.model.misc.ValType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MarkovLaw extends Law {

    private ValType type;
    private List<Edge> list;

    public MarkovLaw(String name) {
        super(name, Type.MARKOV);
    }

    @Override
    public ValType getValType() {
        return type;
    }

    public void setValType(ValType type) {
        this.type = type;
    }

    public List<Edge> getList() {
        return list;
    }

    public void setList(List<Edge> list) {
        final Map<Object, TreeMap<Object, Double>> edgeMap = new HashMap<>();
        for(Edge e : list) {
            if (!edgeMap.containsKey(e.getFrom())) {
                edgeMap.put(e.getFrom(), new TreeMap<>());
            }

            edgeMap.get(e.getFrom()).put(e.getTo(), e.getProba());
        }

        edgeMap.forEach((o, e) -> {
            Double totalWeight = e.entrySet().stream().mapToDouble(Map.Entry::getValue).sum();
            if (totalWeight != 1.0) {
                throw new IllegalArgumentException("total weight is " + totalWeight + " but must be between 1.0 (for value: " + o + ") of law " + getName());
            }
        });

        this.list = list;
    }
}
