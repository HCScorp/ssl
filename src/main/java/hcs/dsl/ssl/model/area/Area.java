package hcs.dsl.ssl.model.area;

import java.util.List;

public class Area {

    private final String name;
    private final List<SensorGroup> sensorGroups;

    public Area(String name, List<SensorGroup> sensorGroups) {
        this.name = name;
        this.sensorGroups = sensorGroups;
    }

    public String getName() {
        return name;
    }

    public List<SensorGroup> getSensorGroups() {
        return sensorGroups;
    }
}
