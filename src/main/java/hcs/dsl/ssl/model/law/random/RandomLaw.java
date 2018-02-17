package hcs.dsl.ssl.model.law.random;

import hcs.dsl.ssl.model.law.Law;
import hcs.dsl.ssl.model.misc.Interval;
import hcs.dsl.ssl.model.misc.ListWrapper;
import hcs.dsl.ssl.model.misc.VarType;


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

    public VarType getValType() {
        return list != null ? list.getType() : VarType.valueOf(interval.type.name());
    }
}
