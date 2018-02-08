package runtime.sensor;

import runtime.period.Period;
import runtime.offset.Offset;
import runtime.source.Source;

import java.io.Serializable;

public class Sensor<T extends Serializable> implements Runnable {

    private String name;
    private Source<T> source; // a well defined law OR a complete CSV,
    private Offset offset;
    private Period period;


    // TODO only update according to its period

    public T produceValue(long timestamp) {
        // TODO check period
        // TODO compute offset
        // TODO get value from source using modified timestamp
        return null; // TODO
    }

    public long getPeriod() {
        return period.getPeriod();
    }

    @Override
    public void run() {

    }
}
