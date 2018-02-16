package hcs.dsl.ssl.model.law.function;

import hcs.dsl.ssl.model.law.Law;
import hcs.dsl.ssl.model.misc.Var;

import java.util.List;

public class FunctionLaw extends Law {

    private List<CaseFunc> cases;

    public FunctionLaw(String name) {
        super(name, Type.FUNCTION);
    }

    public List<CaseFunc> getCases() {
        return cases;
    }

    public void setCases(List<CaseFunc> cases) {
        this.cases = cases;
    }

    @Override
    public Var.Type getValType() {
        return Var.Type.Double;
    }
}
