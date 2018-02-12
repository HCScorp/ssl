package hcs.dsl.ssl.backend.sensor;

import hcs.dsl.ssl.backend.misc.Interval;

import static hcs.dsl.ssl.backend.check.Checker.checkOffset;

public class Sensor {

    private final String name;

    private Source source;
    private Interval noise;
    private String offset;
    private String period;


    public Sensor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
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
        checkOffset(offset);

        this.offset = offset;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
