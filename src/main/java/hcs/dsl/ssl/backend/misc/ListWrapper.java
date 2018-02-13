package hcs.dsl.ssl.backend.misc;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        if (type == Var.Type.String){
            return list.stream().map(content -> "\"" + content + "\"")
                    .map(Object::toString).collect(Collectors.joining(",")).toString();
        }
        return list.toString().replace("[", "").replace("]", "") ;
    }
}
