package hcs.dsl.ssl.model.law.markov;

import hcs.dsl.ssl.model.law.Law;
import hcs.dsl.ssl.model.misc.ValType;

import java.util.List;

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
        this.list = list;
        // TODO check proba sum
    }
}
