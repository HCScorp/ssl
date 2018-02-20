package hcs.dsl.ssl.model.misc;

import java.util.List;
import java.util.stream.Collectors;

public class ListWrapper {

    private final List list;
    private final ValType type;

    public ListWrapper(ValType type, List list) {
        this.list = list;
        this.type = type;
    }

    public List getList() {
        return list;
    }

    public ValType getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type == ValType.String) {
            return list.stream().map(content -> "\"" + content + "\"")
                    .map(Object::toString).collect(Collectors.joining(",")).toString();
        }
        return list.toString().replace("[", "").replace("]", "");
    }
}
