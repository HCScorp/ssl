package hcs.dsl.ssl.runtime.law.function;

import java.math.BigDecimal;

public abstract class FunctionLawInteger extends FunctionLaw<Integer> {

    @Override
    protected Integer eval(BigDecimal val) {
        if (val == null) {
            return 0;
        }

        return val.intValue();
    }
}