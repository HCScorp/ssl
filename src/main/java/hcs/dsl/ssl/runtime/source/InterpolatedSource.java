package hcs.dsl.ssl.runtime.source;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;

public abstract class InterpolatedSource<T extends Number> extends Source<T> {

    public static final String TIMESTAMP_VAR = "x";

    private final Expression expression;

    public InterpolatedSource(Expression expression) {
        this.expression = expression;
    }

    protected BigDecimal eval(long timestamp) {
        return expression.with(TIMESTAMP_VAR, BigDecimal.valueOf(timestamp)).eval();
    }
}
