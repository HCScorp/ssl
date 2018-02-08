package runtime.law.function;

import com.udojava.evalex.Expression;
import runtime.law.Law;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class FunctionLaw<T extends Serializable> extends Law<T> {

    private static final String TIMESTAMP_VAR = "x";

    private class CondExpr {
        private Expression condition;
        private Expression expression;
    }

    private final List<CondExpr> condExprList;

    public FunctionLaw() {
        this.condExprList = new ArrayList<>();
    }

    protected void addCase(String condition, String expression) {
        CondExpr ce = new CondExpr();

        checkCondition(condition, ce);
        checkExpression(expression, ce);

        this.condExprList.add(ce);
    }

    private void checkCondition(String condition, CondExpr ce) {
        if (TIMESTAMP_VAR.equals(condition)) {
            condition = "TRUE";
        }

        Expression cond = new Expression(condition);

        if (!cond.isBoolean() && !condition.equals("TRUE")) {
            throw new IllegalArgumentException("condition \"" + condition + "\" must be a boolean expression (e.g. TRUE) or 'x'");
        }

        if (cond.getUsedVariables().size() > 0 && !cond.getUsedVariables().contains(TIMESTAMP_VAR)) {
            throw new IllegalArgumentException("condition \"" + condition + "\" must only use the variable " + TIMESTAMP_VAR + " that represent the timestamp");
        }

        cond.with(TIMESTAMP_VAR, BigDecimal.ZERO).eval();

        ce.condition = cond;
    }

    private void checkExpression(String expression, CondExpr ce) {
        Expression expr = new Expression(expression);
        expr.with(TIMESTAMP_VAR, BigDecimal.ZERO).eval();

        ce.expression = expr;
    }

    @Override
    public T produceValue(long timestamp) {
        BigDecimal bTimestamp = BigDecimal.valueOf(timestamp);
        for (CondExpr ce : condExprList) {
            Expression cond = ce.condition.with(TIMESTAMP_VAR, bTimestamp);
            BigDecimal val = cond.eval();
            if (!BigDecimal.ZERO.equals(val)) {
                Expression expr = ce.expression.with(TIMESTAMP_VAR, bTimestamp);
                return eval(expr.eval());
            }
        }
        return eval(BigDecimal.ZERO);
    }

    protected abstract T eval(BigDecimal val);
}
