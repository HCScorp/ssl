package hcs.dsl.ssl.runtime.area;

import hcs.dsl.ssl.runtime.noise.Noise;
import hcs.dsl.ssl.runtime.sensor.NoisableSensor;
import hcs.dsl.ssl.runtime.sensor.Sensor;
import org.influxdb.InfluxDB;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SensorGroup implements Runnable {

    private List<Sensor> sensors = new ArrayList<>();

    public <T extends Number> SensorGroup(int number,
                                          Class<? extends NoisableSensor<T>> sensorClass,
                                          Noise<T> noiseOverride) {
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
    }

    public SensorGroup(int number, Class<? extends Sensor> sensorClass) {
        for (int i = 0; i < number; i++) {
            Sensor s;
            try {
                s = sensorClass.newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }

            sensors.add(s);
        }
    }

    public void applyOffset(long offset) {
        for (Sensor s : sensors) {
            s.setOffset(offset);
        }
    }

    public void configure(String execName, String areaInstance, String areaType, InfluxDB influxDB) {
        for (Sensor s : sensors) {
            s.configure(execName, areaInstance, areaType, influxDB);
        }
    }


    public void process(long start, long end) {
        start = TimeUnit.MILLISECONDS.convert(start, TimeUnit.SECONDS);
        end   = TimeUnit.MILLISECONDS.convert(end, TimeUnit.SECONDS);

        long period = sensors.get(0).getPeriodMs();

        System.out.println("Start feeding InfluxDB for sensors " + sensors.get(0).getName() + " (period: " + period + "ms)");

        for (long i = start; i < end; i += period) {
            for (Sensor s : sensors) {
                s.process(TimeUnit.SECONDS.convert(i, TimeUnit.MILLISECONDS));
            }
        }
    }

    @Override
    public void run() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        for (Sensor s : sensors) {
            executor.scheduleAtFixedRate(s, 0, s.getPeriodMs(), TimeUnit.MILLISECONDS);
        }
    }
}
