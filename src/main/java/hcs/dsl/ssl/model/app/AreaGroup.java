package hcs.dsl.ssl.model.app;

import java.util.List;

public class AreaGroup {

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String instanceName : instanceNames) {
            result.append(",new AreaInstance(" + "new Area_").append(areaRef)
                    .append("(),").append("\"")
                    .append(instanceName).append("\"").append(")");
        }
        return result.toString();
    }

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
