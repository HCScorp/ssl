package hcs.dsl.ssl.runtime.example.sensors;

import hcs.dsl.ssl.runtime.example.file.SourceFile_dataCar;
import hcs.dsl.ssl.runtime.noise.NoiseInteger;
import hcs.dsl.ssl.runtime.period.Period;
import hcs.dsl.ssl.runtime.sensor.NoisableSensor;

public class Sensor_capteurNombreVoiture extends NoisableSensor<Integer> {
    public Sensor_capteurNombreVoiture() {
        super("capteurNombreVoiture",
                new SourceFile_dataCar(),
                new Period("10m"),
                new NoiseInteger(0, 2));
    }
}
