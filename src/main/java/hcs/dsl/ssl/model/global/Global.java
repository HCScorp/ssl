package hcs.dsl.ssl.model.global;


import java.util.HashMap;
import java.util.Map;

import static hcs.dsl.ssl.model.check.CheckHelper.checkDate;

public class Global {

    private boolean realtime;

    private String offset;

    private String start;
    private String end;

    public void setOffset(String offset) {
        checkDate(offset);

        this.offset = offset;
    }

    public void setReplay(String start, String end) {
        if (checkDate(start).isBefore(checkDate(end))) {
            throw new IllegalArgumentException("invalid replay date definition: start date must be before the end date");
        }

        this.start = start;
        this.end = end;
    }

    public void setRealtime() {
        this.realtime = true;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public boolean isRealtime() {
        return realtime;
    }

    @Override
    public String toString() {
        Map<String, String> varStatus = new HashMap<>();

        if (offset != null) {
            varStatus.put("offset", "\"" + offset + "\"");
        }
        if (start != null) {
            varStatus.put("start", "\"" + start + "\"");
        }
        if (end != null) {
            varStatus.put("end", "\"" + end + "\"");
        }

        return ",new Config(" + Boolean.toString(realtime) + ","
                + varStatus.get("offset") + "," + varStatus.get("start") + ","
                + varStatus.get("end") + ")";
    }
}
