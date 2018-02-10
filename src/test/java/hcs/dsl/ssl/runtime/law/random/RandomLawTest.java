package hcs.dsl.ssl.runtime.law.random;

import org.junit.Test;
import hcs.dsl.ssl.runtime.example.laws.Law_temperatureEnsemble;
import hcs.dsl.ssl.runtime.example.laws.Law_temperatureIntervalle;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RandomLawTest {
    @Test
    public void temperatureIntervalleTest() {
        Law_temperatureIntervalle l = new Law_temperatureIntervalle();

        for (int i = 0; i < 1000; i++) {
            Double val = l.produceValue(0);
            assertTrue(val >= 18.0 && val <= 24.5);
        }
    }

    @Test
    public void temperatureEnsembleTest() {
        Law_temperatureEnsemble l = new Law_temperatureEnsemble();

        List<Integer> possibleValues = Arrays.asList(18, 12, 42, 24);

        for (int i = 0; i < 100; i++) {
            Integer val = l.produceValue(0);
            assertTrue(possibleValues.contains(val));
        }

    }
}
