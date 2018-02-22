package hcs.dsl.ssl.model.law.file;

import hcs.dsl.ssl.model.misc.Interval;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.List;

public class Interpolation {

    private Interval restriction;
    private PolynomialSplineFunction polynomialFc;

    public void setRestriction(Interval restriction) {
        this.restriction = restriction;
    }

    public Interval getRestriction() {
        return restriction;
    }

    private String codeRestriction() {
        return restriction != null ? "new Restriction<>" + "(" + restriction + ")" : "null";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(codeRestriction()).append(",");

        sb.append("new double[] {")
                .append(join(polynomialFc.getKnots()))
                .append("}");


        for (PolynomialFunction pf : polynomialFc.getPolynomials()) {
            sb.append(", new PolynomialFunction(new double[]{")
                    .append(join(pf.getCoefficients()))
                    .append("})");
        }

        return sb.toString();
    }

    private static String join(double[] arr) {
        return StringUtils.join(ArrayUtils.toObject(arr), ",");
    }

    public void setPolynomialFc(PolynomialSplineFunction polynomialFc) {
        this.polynomialFc = polynomialFc;
    }
}
