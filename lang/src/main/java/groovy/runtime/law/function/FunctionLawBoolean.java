package runtime.law.function;

import java.math.BigDecimal;

public abstract class FunctionLawBoolean extends FunctionLaw<Boolean> {

    @Override
    protected Boolean eval(BigDecimal val) {
        return val != null && !BigDecimal.ZERO.equals(val);
    }
}