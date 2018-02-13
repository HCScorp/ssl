package hcs.dsl.ssl.backend.law;

import hcs.dsl.ssl.backend.misc.Interval;
import hcs.dsl.ssl.backend.misc.ListWrapper;


public class RandomLaw extends Law {

    public ListWrapper list;
    public Interval interval;

    public RandomLaw(String name) {
        super(name, Type.RANDOM);
    }

    public ListWrapper getList() {
        return list;
    }

    public void setList(ListWrapper list) {
        this.list = list;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }
}
