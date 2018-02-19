package hcs.dsl.ssl.model;

import hcs.dsl.ssl.model.area.Area;
import hcs.dsl.ssl.model.app.App;
import hcs.dsl.ssl.model.global.Global;
import hcs.dsl.ssl.model.law.Law;
import hcs.dsl.ssl.model.sensor.Sensor;

import java.util.Map;

public class Model {
    private final Map<String, Law> laws;
    private final Map<String, Sensor> sensors;
    private final Map<String, Area> areas;
    private final Map<String, App> apps;
    private final Global global;

    public Model(Map<String, Law> laws,
                 Map<String, Sensor> sensors,
                 Map<String, Area> areas,
                 Map<String, App> apps,
                 Global global) {
        this.laws = laws;
        this.sensors = sensors;
        this.areas = areas;
        this.apps = apps;
        this.global = global;
    }

    public Map<String, Law> getLaws() {
        return laws;
    }

    public Map<String, Sensor> getSensors() {
        return sensors;
    }

    public Map<String, Area> getAreas() {
        return areas;
    }

    public Map<String, App> getApps() {
        return apps;
    }

    public Global getGlobal() {
        return global;
    }
}
