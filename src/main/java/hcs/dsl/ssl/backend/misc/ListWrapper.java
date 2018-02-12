package hcs.dsl.ssl.backend.misc;

import java.util.List;

public class ListWrapper {

    private final List list;
    private final Var.Type type;

    public ListWrapper(Var.Type type, List list) {
        this.list = list;
        this.type = type;
    }

    public List getList() {
        return list;
    }

    public Var.Type getType() {
        return type;
    }
}
