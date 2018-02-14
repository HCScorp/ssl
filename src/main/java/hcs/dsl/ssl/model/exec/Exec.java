package hcs.dsl.ssl.model.exec;

import hcs.dsl.ssl.model.Namable;

import java.util.List;

public class Exec implements Namable {

    private final String name;
    private final List<AreaGroup> areaGroups;

    public Exec(String name, List<AreaGroup> areaGroups) {
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
