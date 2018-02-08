package runtime.law.function;

import java.math.BigDecimal;

public abstract class FunctionLawDouble extends FunctionLaw<Double> {

    @Override
    protected Double eval(BigDecimal val) {
        if (val == null) {
            return 0.0;
        }

        return val.doubleValue();
    }
}
