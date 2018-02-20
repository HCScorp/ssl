package hcs.dsl.ssl.model.law.function;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;

public class CaseFunc {

    private static final String TS_VAR = "x";

    private final String condition;
    private String expression;
    private String strVal;
    private Integer intVal;
    private Double doubleVal;
    private Boolean boolVal;

    public CaseFunc(String condition) {
        if (TS_VAR.equals(condition)) {
            condition = "TRUE";
        }

        checkCondition(condition);
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        checkExpression(expression);

        this.expression = expression;
    }

    public String getStrVal() {
        return strVal;
    }

    public void setStrVal(String strVal) {
        this.strVal = strVal;
    }

    public Integer getIntVal() {
        return intVal;
    }

    public void setIntVal(Integer intVal) {
        this.intVal = intVal;
    }

    public Double getDoubleVal() {
        return doubleVal;
    }

    public void setDoubleVal(Double doubleVal) {
        this.doubleVal = doubleVal;
    }

    public Boolean getBoolVal() {
        return boolVal;
    }

    public void setBoolVal(Boolean boolVal) {
        this.boolVal = boolVal;
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

    public String toCode() {
        String base = "\"" + condition + "\", ";
        if (expression != null) {
            return base + "\"" + expression + "\"";
        } else if (strVal != null) {
            return base + "\"" + strVal + "\"";
        } else if (intVal != null) {
            return base + intVal;
        } else if (doubleVal != null) {
            return base + doubleVal;
        } else if (boolVal != null) {
            return base + boolVal;
        }

        return null;
    }
}
