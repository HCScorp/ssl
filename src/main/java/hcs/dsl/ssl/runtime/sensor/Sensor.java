package hcs.dsl.ssl.runtime.sensor;

import hcs.dsl.ssl.runtime.source.Source;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Sensor<T extends Serializable> implements Runnable {

    private final String name;
    private final Source<T> source; // a well defined law OR a complete CSV,
    private final long period;

    private long offset = 0;

    private InfluxDB influxDB;

    public Sensor(String name, Source<T> source, long period) {
        this.name = name;
        this.source = source;
        this.period = period;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public void setInfluxDB(InfluxDB influxDB) {
        this.influxDB = influxDB;
    }

    public T produceValue(long timestamp) {
        return source.produceValue(timestamp + offset);
    }

    public String getName() {
        return name;
    }

    public long getPeriod() {
        return period;
    }

    @Override
    public void run() {
        process(System.currentTimeMillis() / 1000);
    }

    public void process(long timestamp) {
        T val = produceValue(timestamp);

        Point.Builder builder = Point.measurement(name).time(timestamp, TimeUnit.SECONDS);

        if (val instanceof Number) {
            builder.addField("value", (Number) val);
        } else if (val instanceof Boolean) {
            builder.addField("value", (Boolean) val);
        } else if (val instanceof String) {
            builder.addField("value", (String) val);
        }

        influxDB.write(builder.build());
    }
}
