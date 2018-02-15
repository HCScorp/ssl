package hcs.dsl.ssl.model.law;

import hcs.dsl.ssl.model.misc.Var;

import java.util.Comparator;

public class SensorData implements Comparable<SensorData> {

    private final long timestamp; // this must be a timestamp in seconds
    private final String name;

    private final Double vDouble;
    private final String vString;
    private final Integer vInteger;
    private final Boolean vBoolean;

    public SensorData(long timestamp, String name, Var.Type type, String raw) {
        this.timestamp = timestamp;
        this.name = name;

        if (type == Var.Type.Integer) {
            vInteger = Integer.parseInt(raw);
            vDouble = null; vBoolean = null; vString = null;
        } else if (type == Var.Type.Double) {
            vDouble = Double.parseDouble(raw);
            vInteger = null; vBoolean = null; vString = null;
        } else if (type == Var.Type.Boolean) {
            vBoolean = Boolean.parseBoolean(raw);
            vDouble = null; vInteger = null; vString = null;
        } else {
            vString = raw;
            vDouble = null; vBoolean = null; vInteger = null;
        }
    }

    public long getTimestamp() {
        return timestamp;
    }


    public String getName() {
        return name;
    }

    public Double getDouble() {
        return vDouble;
    }

    public String getString() {
        return vString;
    }

    public Integer getInteger() {
        return vInteger;
    }

    public Boolean getBoolean() {
        return vBoolean;
    }

    @Override
    public int compareTo(SensorData other) {
        return Comparator.comparingLong(SensorData::getTimestamp).compare(this, other);
    }
}
