package hcs.dsl.ssl.runtime.example.areas;

import hcs.dsl.ssl.runtime.area.AreaType;
import hcs.dsl.ssl.runtime.area.SensorGroup;
import hcs.dsl.ssl.runtime.example.sensors.Sensor_capteurLumiFenetre;
import hcs.dsl.ssl.runtime.example.sensors.Sensor_capteurTempSalle;
import hcs.dsl.ssl.runtime.noise.NoiseInteger;

public class Area_salleDeClasse extends AreaType {

    public Area_salleDeClasse() throws InstantiationException, IllegalAccessException {
        super(
                new SensorGroup(3, Sensor_capteurLumiFenetre.class, false),
                new SensorGroup(1, Sensor_capteurTempSalle.class, new NoiseInteger(1, 4), true)
        );
    }


}