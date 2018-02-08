package runtime.source;

import com.udojava.evalex.Expression;

public class InterpolatedSourceInteger extends InterpolatedSource<Integer> {

    public InterpolatedSourceInteger(Expression expression) {
        super(expression);
    }

    @Override
    public Integer produceValue(long timestamp) {
        return eval(timestamp).intValue();
    }
}
