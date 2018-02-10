package hcs.dsl.ssl.runtime.source;

import org.junit.Test;
import hcs.dsl.ssl.runtime.example.file.SourceFile_dataCar;

import static org.junit.Assert.assertEquals;

public class InterpolatedSourceTest {

    @Test
    public void dataCarTest() {
        SourceFile_dataCar s = new SourceFile_dataCar();
        assertEquals(2, s.produceValue(0).intValue());
    }
}
