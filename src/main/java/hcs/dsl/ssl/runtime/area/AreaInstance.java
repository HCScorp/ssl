package hcs.dsl.ssl.runtime.area;

import org.influxdb.InfluxDB;

public class AreaInstance implements Runnable {

    private final AreaType areaType;
    private final String name;

    public AreaInstance(AreaType areaType, String name) {
        this.areaType = areaType;
        this.name = name;
    }

    public void applyOffset(long offset) {
        areaType.applyOffset(offset);
    }

    public void configure(InfluxDB influxDB) {
        areaType.configure(influxDB);
    }

    public void process(long timestamp) {
        areaType.process(timestamp);
    }

    @Override
    public void run() {
        areaType.run();
    }
}
