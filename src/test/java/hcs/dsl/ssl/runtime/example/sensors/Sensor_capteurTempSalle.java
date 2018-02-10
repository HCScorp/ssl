package hcs.dsl.ssl.runtime.example.sensors;

import hcs.dsl.ssl.runtime.example.laws.Law_temperatureIntervalle;
import hcs.dsl.ssl.runtime.noise.NoiseDouble;
import hcs.dsl.ssl.runtime.period.Period;
import hcs.dsl.ssl.runtime.sensor.NoisableSensor;

public class Sensor_capteurTempSalle extends NoisableSensor<Double> {
    public Sensor_capteurTempSalle() {
        super("capteurTempSalle",
                new Law_temperatureIntervalle(),
                new Period("90s"),
                new NoiseDouble(-0.05, +0.02));
    }
}
