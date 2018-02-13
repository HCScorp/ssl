package hcs.dsl.ssl.runtime.sensor;

import hcs.dsl.ssl.runtime.source.Source;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Sensor<T extends Serializable> implements Runnable {

    private final String name;
    private final Source<T> source; // a well defined law OR a complete CSV,
    private final long periodMs;

//    private long internalOffset;

    private String areaInstance;
    private String areaType;
    private String exec;

    private long offset = 0;

    private InfluxDB influxDB;

    public Sensor(String name, Source<T> source, long periodMs) { //  String internalOffset,
        this.name = name;
        this.source = source;
        this.periodMs = periodMs;
//        this.internalOffset = timestampOf(internalOffset);
    }
//
//    private long timestampOf(String internalOffset) {
//        LocalDateTime epoch = LocalDateTime.parse(internalOffset, Exec.DTF);
//
//        return epoch.atZone(ZoneId.systemDefault()).toEpochSecond();
//    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public void configure(String execName, String areaInstance, String areaType, InfluxDB influxDB) {
        this.exec = execName;
        this.areaInstance = areaInstance;
        this.areaType = areaType;
        this.influxDB = influxDB;
    }

    public T produceValue(long timestamp) {
        return source.produceValue(timestamp + offset);
    }

    public String getName() {
        return name;
    }

    public long getPeriodMs() {
        return periodMs;
    }

    @Override
    public void run() {
        process(TimeUnit.SECONDS.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS));
    }

    public void process(long timestamp) {
        T val = produceValue(timestamp);

        Point.Builder builder = Point.measurement(name)
                .tag("areaType", areaType)
                .tag("areaInstance", areaInstance)
                .tag("exec", exec)
                .time(timestamp, TimeUnit.SECONDS);

        if (val instanceof Number) {
            builder.addField("value", (Number) val);
        } else if (val instanceof Boolean) {
            builder.addField("value", (Boolean) val);
        } else if (val instanceof String) {
            builder.addField("value", (String) val);
        } else {
            // TODO error ? impossibru
        }


        influxDB.write(builder.build());
    }
}
