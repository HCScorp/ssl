package hcs.dsl.ssl.model.law.markov;

import hcs.dsl.ssl.model.law.Law;
import hcs.dsl.ssl.model.misc.VarType;

import java.util.List;

public class MarkovLaw extends Law {

    private VarType type;
    private List<Edge> list;

    public MarkovLaw(String name) {
        super(name, Type.MARKOV);
    }

    @Override
    public VarType getValType() {
        return type;
    }

    public void setValType(VarType type) {
        this.type = type;
    }

    public List<Edge> getList() {
        return list;
    }

    public void setList(List<Edge> list) {
        this.list = list;
        // TODO check proba sum
    }
}
