package kernel.area;

import kernel.noise.Noise;
import kernel.sensor.Sensor;

public class SensorInstance<T extends Number> {

    private Sensor<T> sensor;
    private Integer nbOfSensors;
    private Noise<T> noiseOverride;
    private Boolean parallelMode;

    // TODO
}
