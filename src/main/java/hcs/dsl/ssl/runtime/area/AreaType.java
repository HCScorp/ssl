package hcs.dsl.ssl.runtime.area;

import org.influxdb.InfluxDB;


public class AreaType implements Runnable {

    private final SensorGroup[] sensorGroups;
    private final String name;

    public AreaType(String name, SensorGroup... sensorGroups) {
        this.name = name;
        this.sensorGroups = sensorGroups;
    }

    public String getName() {
        return name;
    }

    public void applyOffset(long offset) {
        for (SensorGroup sg : sensorGroups) {
            sg.applyOffset(offset);
        }
    }

    public void configure(String execName, String areaInstance, InfluxDB influxDB) {
        for (SensorGroup sg : sensorGroups) {
            sg.configure(execName, areaInstance, name, influxDB);
        }
    }

    public void process(long start, long end) {
        for (SensorGroup sg : sensorGroups) {
            sg.process(start, end);
        }
    }

    @Override
    public void run() {
        for (SensorGroup sg : sensorGroups) {
            new Thread(sg).start();
        }
    }
}
