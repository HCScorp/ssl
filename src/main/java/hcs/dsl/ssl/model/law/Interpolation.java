package hcs.dsl.ssl.model.law;

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


}
