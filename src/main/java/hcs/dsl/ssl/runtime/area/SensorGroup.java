package hcs.dsl.ssl.runtime.area;

import hcs.dsl.ssl.runtime.noise.Noise;
import hcs.dsl.ssl.runtime.sensor.NoisableSensor;
import hcs.dsl.ssl.runtime.sensor.Sensor;
import org.influxdb.InfluxDB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SensorGroup implements Runnable {

    private List<Sensor> sensors = new ArrayList<>();
    private boolean parallelMode;

    public <T extends Number> SensorGroup(int number,
                                          Class<? extends NoisableSensor<T>> sensorClass,
                                          Noise<T> noiseOverride,
                                          boolean parallelMode) {
        for (int i = 0; i < number; i++) {
            NoisableSensor s;
            try {
                s = sensorClass.newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }

            if (noiseOverride != null) {
                s.setNoise(noiseOverride);
            }

            sensors.add(s);
        }

        this.parallelMode = parallelMode;
    }

    public SensorGroup(int number,
                       Class<? extends Sensor> sensorClass,
                       boolean parallelMode) {
        for (int i = 0; i < number; i++) {
            Sensor s;
            try {
                s = sensorClass.newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }

            sensors.add(s);
        }
        this.parallelMode = parallelMode;
    }

    public void applyOffset(long offset) {
        for (Sensor s : sensors) {
            s.setOffset(offset);
        }
    }

    public void configure(InfluxDB influxDB) {
        for (Sensor s : sensors) {
            s.setInfluxDB(influxDB);
        }
    }


    public void process(long timestamp) {
        for (Sensor s : sensors) {
            s.process(timestamp);
        }
    }

    @Override
    public void run() {
        // TODO use parallelMode information ?

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        for (Sensor s : sensors) {
            executor.scheduleAtFixedRate(s, 0, s.getPeriod(), TimeUnit.SECONDS);
        }
    }
}
