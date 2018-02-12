package hcs.dsl.ssl.runtime.exec;

public class Config {

    private final boolean realtime;
    private final String offset;

    private final String start;
    private final String end;

    public Config(boolean realtime, String offset, String start, String end) {
        this.realtime = realtime;
        this.offset = offset;
        this.start = start;
        this.end = end;
    }

    public boolean isRealtime() {
        return realtime;
    }

    public String getOffset() {
        return offset;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
