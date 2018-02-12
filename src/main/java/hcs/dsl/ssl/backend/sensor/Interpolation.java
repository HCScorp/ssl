package hcs.dsl.ssl.backend.sensor;

import hcs.dsl.ssl.backend.misc.Interval;

public class Interpolation {

    private Interval restriction;

    public void setRestriction(Interval restriction) {
        this.restriction = restriction;
    }

    public Interval getRestriction() {
        return restriction;
    }
}
