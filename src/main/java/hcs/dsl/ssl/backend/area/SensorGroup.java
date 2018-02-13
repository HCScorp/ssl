package hcs.dsl.ssl.backend.area;

import hcs.dsl.ssl.backend.misc.Interval;

public class SensorGroup {

    private final Integer number;
    private final String sensorRef;
    private Interval noise; // todo check noise applicable ?

    @Override
    public String toString() {
        String base = "new SensorGroup(" + number + "," + "Sensor_" + sensorRef;
        String noiseConstruct = "";
        if (noise != null) {
            if (noise.type == Interval.Type.Double) {
                noiseConstruct = ", new NoiseDouble(" + noise + ")";
            } else {
                noiseConstruct = ", new NoiseInteger(" + noise + ")";
            }
        }
        return base + noiseConstruct + ")";
    }


    public SensorGroup(String sensorRef, Integer number) {
        this.sensorRef = sensorRef;
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public String getSensorRef() {
        return sensorRef;
    }

    public Interval getNoise() {
        return noise;
    }

    public void setNoise(Interval noise) {
        this.noise = noise;
    }

}
