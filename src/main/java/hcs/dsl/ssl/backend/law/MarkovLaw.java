package hcs.dsl.ssl.backend.law;

import hcs.dsl.ssl.backend.misc.Var;

import java.util.List;

public class MarkovLaw extends Law {

    private Var.Type type;
    private List<Edge> list;

    public MarkovLaw(String name) {
        super(name, Type.MARKOV);
    }

    public Var.Type getValType() {
        return type;
    }

    public void setValType(Var.Type type) {
        this.type = type;
    }

    public List<Edge> getList() {
        return list;
    }

    public void setList(List<Edge> list) {
        this.list = list;
    }
}
