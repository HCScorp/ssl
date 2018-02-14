package hcs.dsl.ssl.model;

import hcs.dsl.ssl.model.area.Area;
import hcs.dsl.ssl.model.exec.Exec;
import hcs.dsl.ssl.model.global.Global;
import hcs.dsl.ssl.model.law.Law;
import hcs.dsl.ssl.model.sensor.Sensor;

import java.util.Map;

public class Model {
    public final Map<String, Law> laws;
    public final Map<String, Sensor> sensors;
    public final Map<String, Area> areas;
    public final Map<String, Exec> execs;
    public final Global global;

    public Model(Map<String, Law> laws,
                 Map<String, Sensor> sensors,
                 Map<String, Area> areas,
                 Map<String, Exec> execs,
                 Global global) {
        this.laws = laws;
        this.sensors = sensors;
        this.areas = areas;
        this.execs = execs;
        this.global = global;
    }


}
