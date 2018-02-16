package hcs.dsl.ssl.model.law.markov;

import java.io.Serializable;

public class Edge<T extends Serializable> {
    public T from;
    public Double proba;
    public T to;

    public Edge(T from, Double proba, T to) {
        this.from = from;
        this.proba = proba;
        this.to = to;
    }

    public T getFrom() {
        return from;
    }

    public Double getProba() {
        return proba;
    }

    public T getTo() {
        return to;
    }

}
