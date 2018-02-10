package hcs.dsl.ssl.runtime.sensor;

import hcs.dsl.ssl.runtime.offset.Offset;
import hcs.dsl.ssl.runtime.period.Period;
import hcs.dsl.ssl.runtime.source.Source;

import java.io.Serializable;

public class Sensor<T extends Serializable> implements Runnable {

    private final String name;
    private final Source<T> source; // a well defined law OR a complete CSV,
    private final Period period;

//    private OutputInfluxDB out;
    private Offset offset;

    public Sensor(String name, Source<T> source, Period period) {
        this.name = name;
        this.source = source;
        this.period = period;
    }

    public void setOffset(Offset offset) {
        this.offset = offset;
    }

    public T produceValue(long timestamp) {
        return source.produceValue(offset.apply(timestamp));
    }

    public long getPeriod() {
        return period.getPeriod();
    }

    @Override
    public void run() {
        // TODO send produced value to influx db
    }
}
