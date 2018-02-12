package hcs.dsl.ssl.backend.law;

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
}
