package hcs.dsl.ssl.runtime.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorData<V> {
    @JsonProperty("time")
    private long time; // TODO this is produced data, the input can be a date or an hour

    @JsonProperty("sensor")
    private String sensor;

    @JsonProperty("value")
    private V value;

    public SensorData() {
        // empty for jackson
    }
}
