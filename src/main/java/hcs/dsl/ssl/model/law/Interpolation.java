package hcs.dsl.ssl.model.law;

import hcs.dsl.ssl.model.misc.Interval;

public class Interpolation {

    private Interval restriction;

    public void setRestriction(Interval restriction) {
        this.restriction = restriction;
    }

    public Interval getRestriction() {
        return restriction;
    }
}
