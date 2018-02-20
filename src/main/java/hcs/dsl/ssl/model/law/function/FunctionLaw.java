package hcs.dsl.ssl.model.law.function;

import hcs.dsl.ssl.model.law.Law;
import hcs.dsl.ssl.model.misc.ValType;

import java.util.List;

public class FunctionLaw extends Law {

    private List<CaseFunc> cases;
    private ValType valType;
    private boolean expr;

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
    public ValType getValType() {
        return valType;
    }

    public void setValType(ValType valType) {
        this.valType = valType;
    }

    public void setExpr() {
        this.expr = true;
    }

    public String getFcType() {
        if (expr) {
            return "Expr";
        }

        return valType.name();
    }
}
