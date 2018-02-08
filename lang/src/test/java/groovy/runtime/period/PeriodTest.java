package runtime.period;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PeriodTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullPeriodTest() {
        new Period("0s");
    }


    @Test(expected = IllegalArgumentException.class)
    public void wrongPatternTest() {
        new Period("15.42 minutes");
    }


    @Test
    public void period200ms() {
        Period f = new Period("200ms");
        assertEquals(200, f.getPeriod());
    }

    @Test
    public void period10s() {
        Period f = new Period("10s");
        assertEquals(10000, f.getPeriod());
    }
}
