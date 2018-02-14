package hcs.dsl.ssl.model.law;

import hcs.dsl.ssl.model.misc.Interval;
import hcs.dsl.ssl.model.misc.ListWrapper;
import hcs.dsl.ssl.model.misc.Var;


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

    public Var.Type getValType() {
        return list != null ? list.getType() : Var.Type.valueOf(interval.type.name());
    }
}
