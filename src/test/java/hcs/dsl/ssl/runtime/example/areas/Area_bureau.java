package hcs.dsl.ssl.runtime.example.areas;

import hcs.dsl.ssl.runtime.area.AreaType;
import hcs.dsl.ssl.runtime.area.SensorGroup;
import hcs.dsl.ssl.runtime.example.sensors.Sensor_capteurNombreVoiture;
import hcs.dsl.ssl.runtime.example.sensors.Sensor_capteurTempBureau;

public class Area_bureau extends AreaType {

    public Area_bureau() {
        super(
                new SensorGroup(2, Sensor_capteurTempBureau.class, null, false)
        );
    }
}
