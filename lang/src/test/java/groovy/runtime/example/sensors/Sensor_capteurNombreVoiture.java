package runtime.example.sensors;

import runtime.example.file.SourceFile_dataCar;
import runtime.noise.NoiseInteger;
import runtime.period.Period;
import runtime.sensor.NoisableSensor;

public class Sensor_capteurNombreVoiture extends NoisableSensor<Integer> {
    public Sensor_capteurNombreVoiture() {
        super("capteurNombreVoiture",
                new SourceFile_dataCar(),
                new Period("10m"),
                new NoiseInteger(0, 2));
    }
}
