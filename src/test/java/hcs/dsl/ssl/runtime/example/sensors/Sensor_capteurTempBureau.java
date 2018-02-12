package hcs.dsl.ssl.runtime.example.sensors;

import hcs.dsl.ssl.runtime.example.laws.Law_temperatureEnsemble;
import hcs.dsl.ssl.runtime.sensor.NoisableSensor;

public class Sensor_capteurTempBureau extends NoisableSensor<Integer> {
    public Sensor_capteurTempBureau() {
        super("capteurTempBureau",
                new Law_temperatureEnsemble(),
                60000,
                null);
    }
}
