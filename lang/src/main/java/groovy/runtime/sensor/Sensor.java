package runtime.sensor;

import runtime.frequency.Frequency;
import runtime.noise.Noise;
import runtime.offset.Offset;
import runtime.source.Source;

import java.io.Serializable;

public class Sensor<T extends Serializable> {

    private String name;
    private Source<T> source; // a well defined law OR a complete CSV,
    private Offset offset;
    private Frequency frequency;


    // TODO only update according to its frequency

    public T produceValue(long timestamp) {
        // TODO check frequency
        // TODO compute offset
        // TODO get value from source using modified timestamp
        return null; // TODO
    }
}
