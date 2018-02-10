package hcs.dsl.ssl.runtime.example.sensors;

import hcs.dsl.ssl.runtime.example.laws.Law_lumiDehors;
import hcs.dsl.ssl.runtime.period.Period;
import hcs.dsl.ssl.runtime.sensor.Sensor;
import hcs.dsl.ssl.runtime.source.Source;

public class Sensor_capteurLumiFenetre extends Sensor<String> {
    public Sensor_capteurLumiFenetre() {
        super("capteurLumiFenetre",
                new Law_lumiDehors(),
                new Period("5m"));
    }
}
