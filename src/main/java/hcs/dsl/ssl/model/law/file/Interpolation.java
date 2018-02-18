package hcs.dsl.ssl.model.law.file;

import hcs.dsl.ssl.model.misc.Interval;

import java.util.List;

public class Interpolation {

    private Interval restriction;

    public List<Double> getCoefPolynome() {
        return coefPolynome;
    }

    public void setCoefPolynome(List<Double> coefPolynome) {
        this.coefPolynome = coefPolynome;
    }

    private List<Double> coefPolynome;

    public void setRestriction(Interval restriction) {
        this.restriction = restriction;
    }

    public Interval getRestriction() {
        return restriction;
    }

    public String codeRestriction() {
        if (restriction != null) {
            return ",new Restriction<>" + "(" + restriction + ")";
        }
        return "";
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(coefPolynome.get(0));
        for (int i = 1; i < coefPolynome.size(); i++) {
            result.append("+").append(coefPolynome.get(i)).append("*x^").append(i);
        }


        return result.toString();
    }
}
