package hcs.dsl.ssl.model.app;

import hcs.dsl.ssl.model.Namable;

import java.util.List;

public class App implements Namable {

    private final String name;
    private final List<AreaGroup> areaGroups;

    public App(String name, List<AreaGroup> areaGroups) {
        this.name = name;
        this.areaGroups = areaGroups;
    }

    public String getName() {
        return name;
    }

    public List<AreaGroup> getAreaGroups() {
        return areaGroups;
    }
}
