package hcs.dsl.ssl.runtime.example.laws;

import hcs.dsl.ssl.runtime.law.random.RandomLawArray;

public class Law_temperatureEnsemble extends RandomLawArray<Integer> {
    public Law_temperatureEnsemble() { // public constructor
        super(16, 18, 19, 20, 22);
    }
}
