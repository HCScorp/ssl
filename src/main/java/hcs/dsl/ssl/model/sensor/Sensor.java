package hcs.dsl.ssl.model.sensor;

import hcs.dsl.ssl.model.Namable;
import hcs.dsl.ssl.model.misc.Interval;

import static hcs.dsl.ssl.model.check.Checker.checkDate;

public class Sensor implements Namable {

    private final String name;

    private String lawRef;
    private Interval noise;
    private String offset;
    private Period period;

    public Sensor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getLawRef() {
        return lawRef;
    }

    public void setLawRef(String lawRef) {
        this.lawRef = lawRef;
    }

    public Interval getNoise() {
        return noise;
    }

    public void setNoise(Interval noise) {
        this.noise = noise; // todo check noise applicable ?
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        checkDate(offset);

        this.offset = offset;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = new Period(period);
    }
}
