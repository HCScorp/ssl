package hcs.dsl.ssl.runtime.example.file;


import com.udojava.evalex.Expression;
import hcs.dsl.ssl.runtime.source.InterpolatedSourceInteger;

public class SourceFile_dataCar extends InterpolatedSourceInteger {

    public SourceFile_dataCar() {
        // TODO linear interpolation + complete file source
        super(new Expression("a0 + a1*x^2 + a2*x^3 + a3*x^4 + a4*x^5")
                .with("a0", "2")
                .with("a1", "0.0065")
                .with("a2", "0.046")
                .with("a3", "0.2319")
                .with("a4", "-0.0205")
        );
    }
}
