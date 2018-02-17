package hcs.dsl.ssl.model.law.file;

import hcs.dsl.ssl.model.misc.VarType;

import java.util.Comparator;

public class SensorData implements Comparable<SensorData> {

    private final long timestamp; // this must be a timestamp in seconds
    private final String name;

    private final Double vDouble;
    private final String vString;
    private final Integer vInteger;
    private final Boolean vBoolean;

    public SensorData(long timestamp, String name, VarType type, String raw) {
        this.timestamp = timestamp;
        this.name = name;

        if (type == VarType.Integer) {
            vInteger = Integer.parseInt(raw);
            vDouble = null; vBoolean = null; vString = null;
        } else if (type == VarType.Double) {
            vDouble = Double.parseDouble(raw);
            vInteger = null; vBoolean = null; vString = null;
        } else if (type == VarType.Boolean) {
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

    public String toCode(){
        String result = "(" + timestamp + ",";
        if (vBoolean != null){
            result += vBoolean;
        }
        else if(vDouble != null){
            result += vDouble;
        }
        else if (vInteger != null){
            result += vInteger;
        }
        else
            result += vString;
        return result + ")";
    }
}
