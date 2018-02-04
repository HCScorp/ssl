package kernel.formula.law;

import kernel.entity.SensorDataList;

public class RandomLaw implements LawDescriber {

    private String offset;

    @Override
    public void prepare(String offset) {
        this.offset = offset;
    }

    @Override
    public SensorDataList generate() {
        // TODO:: generate the code here.
        return null;
    }
}
