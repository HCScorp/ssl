package hcs.dsl.ssl.runtime.example.areas;

import hcs.dsl.ssl.runtime.area.AreaType;
import hcs.dsl.ssl.runtime.area.SensorGroup;
import hcs.dsl.ssl.runtime.example.sensors.Sensor_capteurNombreVoiture;
import hcs.dsl.ssl.runtime.noise.NoiseInteger;

public class Area_parking extends AreaType {

    public Area_parking() {
        super("parking",
                new SensorGroup(2, Sensor_capteurNombreVoiture.class, new NoiseInteger(1, 6))
        );
    }
}
