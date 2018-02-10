package hcs.dsl.ssl.runtime.example.laws;

import hcs.dsl.ssl.runtime.law.markov.MarkovLaw;

public class Law_lumiDehors extends MarkovLaw<String> {

    public Law_lumiDehors() {
        super();

        addEdge("sunny", 0.1, "rainy");
        addEdge("sunny", 0.9, "sunny");
        addEdge("rainy", 0.5, "sunny");
        addEdge("rainy", 0.5, "rainy");
    }

}
