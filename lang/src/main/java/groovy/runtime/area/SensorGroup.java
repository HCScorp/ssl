package runtime.area;

import runtime.noise.Noise;
import runtime.sensor.Sensor;

public class SensorGroup<T extends Number> {

    private Sensor<T> sensor;
    private Integer nbOfSensors;
    private Noise<T> noiseOverride;
    private Boolean parallelMode;

    // TODO
}
