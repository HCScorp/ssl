package kernel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorData<V> {
    @JsonProperty("time")
    private long time; // TODO this is produced data, the input can be a date or an hour

    @JsonProperty("sensor")
    private String sensor;

    @JsonProperty("value")
    private V value;

    public SensorData() {
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "time=" + time +
                ", sensor='" + sensor + '\'' +
                ", value=" + value +
                '}';
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

}
