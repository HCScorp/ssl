package hcs.dsl.ssl.backend.exec;

import java.util.List;

public class Exec {

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
