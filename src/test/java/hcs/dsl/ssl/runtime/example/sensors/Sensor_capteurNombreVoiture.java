package hcs.dsl.ssl.runtime.example.sensors;

import hcs.dsl.ssl.runtime.example.file.SourceFile_dataCar;
import hcs.dsl.ssl.runtime.noise.NoiseInteger;
import hcs.dsl.ssl.runtime.sensor.NoisableSensor;

public class Sensor_capteurNombreVoiture extends NoisableSensor<Integer> {
    public Sensor_capteurNombreVoiture() {
        super("capteurNombreVoiture",
                new SourceFile_dataCar(),
                300000,
                new NoiseInteger(0, 2));
    }
}
