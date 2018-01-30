package kernel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SensorData<V> {

    @JsonProperty("time")
    private long time;

    @JsonProperty("sensor")
    private String sensor;

    @JsonProperty("value")
    private V value;

}
