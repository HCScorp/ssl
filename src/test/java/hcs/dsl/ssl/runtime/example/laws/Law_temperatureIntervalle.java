package hcs.dsl.ssl.runtime.example.laws;

import hcs.dsl.ssl.runtime.law.random.RandomLawIntervalDouble;

public class Law_temperatureIntervalle extends RandomLawIntervalDouble {
    public Law_temperatureIntervalle() { // public constructor
        super(18.0, 24.5);
    }
}
