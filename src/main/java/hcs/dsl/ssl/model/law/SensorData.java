package hcs.dsl.ssl.model.law;

import com.fasterxml.jackson.databind.ObjectMapper;
import hcs.dsl.ssl.model.misc.Var;

public class SensorData implements Comparable<SensorData> {
    private long time; // TODO this must be a timestamp in seconds
    private String name;
    private Double valueDouble; // TODO resolve value
    private String valueString; // TODO resolve value
    private Integer valueInteger; // TODO resolve value
    private Boolean valueBoolean; // TODO resolve value

    public void setProperValue(Var.Type type, String value){
        if (type == Var.Type.Integer)valueInteger = new Integer(value);
        else if (type == Var.Type.Double)valueDouble = new Double(value);
        else if(type == Var.Type.Boolean)valueBoolean = new Boolean(value);
        else valueString = value;
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValueDouble() {
        return valueDouble;
    }

    public void setValueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public Integer getValueInteger() {
        return valueInteger;
    }

    public void setValueInteger(Integer valueInteger) {
        this.valueInteger = valueInteger;
    }

    public Boolean getValueBoolean() {
        return valueBoolean;
    }

    public void setValueBoolean(Boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
    }


    @Override
    public int compareTo(SensorData sensorData) {
        return (int) (this.time - sensorData.time);
    }

}
