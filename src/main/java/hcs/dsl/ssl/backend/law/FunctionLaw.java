package hcs.dsl.ssl.backend.law;

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
}
