package hcs.dsl.ssl.runtime.noise;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NoiseTest {

    @Test
    public void noiseDoubleTest() {
        NoiseDouble noise = new NoiseDouble(0.0, 1.0);
        for (int i = 0; i < 1000; i++) {
            Double val = noise.apply(0.0);
            assertTrue(val >= 0.0 && val <= 1.0);
        }
    }

    @Test
    public void noiseIntegerTest() {
        NoiseInteger noise = new NoiseInteger(0, 10);
        for (int i = 0; i < 1000; i++) {
            Integer val = noise.apply(0);
            assertTrue(val >= 0 && val <= 10);
        }
    }
}
