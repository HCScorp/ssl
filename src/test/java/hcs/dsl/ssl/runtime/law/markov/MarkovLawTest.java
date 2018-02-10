package hcs.dsl.ssl.runtime.law.markov;

import org.junit.Test;
import hcs.dsl.ssl.runtime.example.laws.Law_lumiDehors;

import static org.junit.Assert.assertTrue;

public class MarkovLawTest {
    @Test
    public void lumiDehorsTest() {
        Law_lumiDehors l = new Law_lumiDehors();

        int sunny = 0;
        for (int i = 0; i < 10000; i++) {
            if (l.produceValue(0).equals("sunny")) {
                sunny++;
            }
        }

        assertTrue(sunny > 6000);
    }
}
