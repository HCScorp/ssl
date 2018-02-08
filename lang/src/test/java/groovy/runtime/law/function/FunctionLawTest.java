package runtime.law.function;

import org.junit.Test;
import runtime.example.laws.Law_polyMilieuJournee;

import static org.junit.Assert.assertEquals;

public class FunctionLawTest {
    @Test
    public void polyMilieuJourneeTest() {
        Law_polyMilieuJournee l = new Law_polyMilieuJournee();

        assertEquals(4.0, l.produceValue(0), 0.00001);
        assertEquals(0.0, l.produceValue(32200), 0.00001);
        assertEquals(2.073647E9, l.produceValue(32201), 0.00001);
    }
}
