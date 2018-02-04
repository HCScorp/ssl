package kernel.formula.law;

import kernel.entity.SensorDataList;

import java.util.Map;

public interface LawDescriber {


    void prepare(String offset);

    SensorDataList generate();

}
