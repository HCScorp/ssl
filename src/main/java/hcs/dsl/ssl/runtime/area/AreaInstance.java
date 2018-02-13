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

    public void configure(String execName, InfluxDB influxDB) {
        areaType.configure(execName, name, influxDB);
    }

    public void process(long start, long end) {
        areaType.process(start, end);
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        areaType.run();
    }
}
