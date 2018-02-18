package hcs.dsl.ssl.model.misc;

import java.util.List;
import java.util.stream.Collectors;

public class ListWrapper {

    private final List list;
    private final VarType type;

    public ListWrapper(VarType type, List list) {
        this.list = list;
        this.type = type;
    }

    public List getList() {
        return list;
    }

    public VarType getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type == VarType.String) {
            return list.stream().map(content -> "\"" + content + "\"")
                    .map(Object::toString).collect(Collectors.joining(",")).toString();
        }
        return list.toString().replace("[", "").replace("]", "");
    }
}
