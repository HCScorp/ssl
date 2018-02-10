package hcs.dsl.ssl.runtime.source;

import com.udojava.evalex.Expression;

public class InterpolatedSourceDouble extends InterpolatedSource<Double> {

    public InterpolatedSourceDouble(Expression expression) {
        super(expression);
    }

    @Override
    public Double produceValue(long timestamp) {
        return eval(timestamp).doubleValue();
    }
}
