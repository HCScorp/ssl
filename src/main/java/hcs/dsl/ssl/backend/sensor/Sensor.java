package hcs.dsl.ssl.backend.sensor;

import hcs.dsl.ssl.backend.misc.Interval;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Sensor {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

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
        this.noise = noise;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        try {
            LocalDateTime.parse(offset, dtf);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("invalid offset date: ", e);
        }

        this.offset = offset;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        // TODO check period value
        this.period = period;
    }
}
