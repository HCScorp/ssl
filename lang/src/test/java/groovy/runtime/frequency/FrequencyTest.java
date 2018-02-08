package runtime.frequency;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FrequencyTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullFreqTest() {
        new Frequency("0s");
    }


    @Test(expected = IllegalArgumentException.class)
    public void wrongPatternTest() {
        new Frequency("15.42 minutes");
    }


    @Test
    public void simpleFreqMilliSecond() throws InterruptedException {
        Frequency f = new Frequency("200ms");
        f.update();
        assertFalse(f.canUpdate());
        Thread.sleep(202);
        assertTrue(f.canUpdate());
        f.update();
        assertFalse(f.canUpdate());
    }
}
