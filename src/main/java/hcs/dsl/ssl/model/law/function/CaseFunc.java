package hcs.dsl.ssl.model.law.function;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;

public class CaseFunc {

    private static final String TS_VAR = "x";

    private final String condition;
    private final String expresion;

    public CaseFunc(String condition, String expresion) {
        if (TS_VAR.equals(condition)) {
            condition = "TRUE";
        }

        checkCondition(condition);
        checkExpression(expresion);

        this.condition = condition;
        this.expresion = expresion;
    }

    public String getCondition() {
        return condition;
    }

    public String getExpresion() {
        return expresion;
    }

    private static void checkCondition(String condition) {
        Expression cond;

        try {
            cond = new Expression(condition);

        } catch (Expression.ExpressionException e) {
            throw new IllegalArgumentException("condition \"" + condition + "\" is not valid: ", e);
        }

        if (!cond.isBoolean() && !condition.equals("TRUE")) {
            throw new IllegalArgumentException("condition \"" + condition + "\" must be a boolean expression (e.g. TRUE) or 'x'");
        }

        if (cond.getUsedVariables().size() > 0 && !cond.getUsedVariables().contains(TS_VAR)) {
            throw new IllegalArgumentException("condition \"" + condition + "\" must only use the variable " + TS_VAR + " that represent the timestamp");
        }

        try {
            cond.with(TS_VAR, BigDecimal.ZERO).eval();
        } catch (Expression.ExpressionException e) {
            throw new IllegalArgumentException("condition \"" + condition + "\" is not evaluable: ", e);
        }
    }

    private static void checkExpression(String expression) {
        try {
            Expression expr = new Expression(expression);

            try {
                expr.with(TS_VAR, BigDecimal.ZERO).eval();
            } catch (Expression.ExpressionException e) {
                throw new IllegalArgumentException("expression \"" + expression + "\" is not evaluable: ", e);
            }

        } catch (Expression.ExpressionException e) {
            throw new IllegalArgumentException("expression \"" + expression + "\" is not valid: ", e);
        }
    }
}
