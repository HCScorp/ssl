package hcs.dsl.ssl.runtime.area;

import hcs.dsl.ssl.runtime.noise.Noise;
import hcs.dsl.ssl.runtime.sensor.Sensor;

public class SensorGroup<T extends Number> {

    private Sensor<T> sensor;
    private Integer nbOfSensors;
    private Noise<T> noiseOverride;
    private Boolean parallelMode;

    // TODO
}
