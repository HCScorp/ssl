package hcs.dsl.ssl.backend.exec;

import java.util.List;

public class AreaGroup {

    private final String areaRef;
    private final List<String> instanceNames;

    public AreaGroup(String areaRef, List<String> instanceNames) {
        this.areaRef = areaRef;
        this.instanceNames = instanceNames;
    }

    public String getAreaRef() {
        return areaRef;
    }

    public List<String> getInstanceNames() {
        return instanceNames;
    }
}
