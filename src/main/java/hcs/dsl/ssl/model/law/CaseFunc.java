package hcs.dsl.ssl.model.law;

public class CaseFunc {
    private final String condition;
    private final String expresion;

    public CaseFunc(String condition, String expresion) {
        this.condition = condition;
        this.expresion = expresion;
    }

    public String getCondition() {
        return condition;
    }

    public String getExpresion() {
        return expresion;
    }
}
