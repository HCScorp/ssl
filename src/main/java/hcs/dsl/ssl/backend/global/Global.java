package hcs.dsl.ssl.backend.global;

import static hcs.dsl.ssl.backend.check.Checker.checkOffset;

public class Global {

    private String offset;

    public void setOffset(String offset) {
        checkOffset(offset);

        this.offset = offset;
    }

    public String getOffset() {
        return offset;
    }
}
