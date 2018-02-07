package runtime.sensor;

import runtime.frequency.Frequency;
import runtime.noise.Noise;
import runtime.offset.Offset;
import runtime.source.Source;

import java.io.Serializable;

public class Sensor<T extends Serializable> {

    private String name;
    private Source<T> source; // a well defined law OR a complete CSV,
//    private Noise<T> noise; TODO noise only on noisable sensor, i.e. sensor using number values
    private Offset offset;
    private Frequency frequency;


    // TODO only update according to its frequency
}
