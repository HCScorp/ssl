package hcs.dsl.ssl.backend.area;

import hcs.dsl.ssl.backend.misc.Interval;

public class SensorGroup {

    private final Integer number;
    private final String sensorRef;
    private Interval noise; // todo check noise applicable ?

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
