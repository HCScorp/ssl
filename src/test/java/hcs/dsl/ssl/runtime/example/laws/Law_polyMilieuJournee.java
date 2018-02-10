package hcs.dsl.ssl.runtime.example.laws;

import hcs.dsl.ssl.runtime.law.function.FunctionLawDouble;

public class Law_polyMilieuJournee extends FunctionLawDouble {

    public Law_polyMilieuJournee() {
        super();

        addCase("x%(3600*24) < 32200", "4");
        addCase("x%(3600*24) > 32200", "abs(-(2*x^2) + 5*x - 1)");
        addCase("x%(3600*24) = 32200", "0");
    }
}
