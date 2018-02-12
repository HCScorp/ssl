package hcs.dsl.ssl.runtime.area;

import org.influxdb.InfluxDB;

public class AreaType implements Runnable {

    private final SensorGroup[] sensorGroups;

    public AreaType(SensorGroup... sensorGroups) {
        this.sensorGroups = sensorGroups;
    }

    public void applyOffset(long offset) {
        for (SensorGroup sg : sensorGroups) {
            sg.applyOffset(offset);
        }
    }

    public void configure(InfluxDB influxDB) {
        for (SensorGroup sg : sensorGroups) {
            sg.configure(influxDB);
        }
    }

    public void process(long timestamp) {
        for (SensorGroup sg : sensorGroups) {
            sg.process(timestamp);
        }
    }

    @Override
    public void run() {
        for (SensorGroup sg : sensorGroups) {
            new Thread(sg).start();
        }
    }
}
