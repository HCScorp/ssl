package kernel.sensor;

import kernel.noise.Noise;
import kernel.offset.Offset;
import kernel.source.Source;

public class Sensor<T extends Number> {

    private Source<T> source; // a well defined law OR a complete CSV,
    private Noise<T> noise;
    private Offset offset;


}
