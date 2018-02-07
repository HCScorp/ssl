package runtime.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SensorDataList<V> {

    public SensorDataList() {
    }

    @JsonProperty("data")
    List<SensorData<V>> sensorDataList;

    public List<SensorData<V>> getSensorDataList() {
        return sensorDataList;
    }

    public void setSensorDataList(List<SensorData<V>> sensorDataList) {
        this.sensorDataList = sensorDataList;
    }

}
