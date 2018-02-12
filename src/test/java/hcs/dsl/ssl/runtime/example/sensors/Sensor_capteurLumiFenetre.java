package hcs.dsl.ssl.runtime.example.sensors;

import hcs.dsl.ssl.runtime.example.laws.Law_lumiDehors;
import hcs.dsl.ssl.runtime.sensor.Sensor;

public class Sensor_capteurLumiFenetre extends Sensor<String> {
    public Sensor_capteurLumiFenetre() {
        super("capteurLumiFenetre",
                new Law_lumiDehors(),
                300000);
    }
}
