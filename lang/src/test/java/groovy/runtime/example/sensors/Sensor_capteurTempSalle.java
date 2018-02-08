package runtime.example.sensors;

import runtime.example.laws.Law_temperatureIntervalle;
import runtime.noise.NoiseDouble;
import runtime.period.Period;
import runtime.sensor.NoisableSensor;

public class Sensor_capteurTempSalle extends NoisableSensor<Double> {
    public Sensor_capteurTempSalle() {
        super("capteurTempSalle",
                new Law_temperatureIntervalle(),
                new Period("90s"),
                new NoiseDouble(-0.05, +0.02));
    }
}
