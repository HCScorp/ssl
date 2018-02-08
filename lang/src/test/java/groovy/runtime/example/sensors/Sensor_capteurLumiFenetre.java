package runtime.example.sensors;

import runtime.example.laws.Law_lumiDehors;
import runtime.period.Period;
import runtime.sensor.Sensor;
import runtime.source.Source;

public class Sensor_capteurLumiFenetre extends Sensor<String> {
    public Sensor_capteurLumiFenetre() {
        super("capteurLumiFenetre",
                new Law_lumiDehors(),
                new Period("5m"));
    }
}
