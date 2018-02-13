package hcs.dsl.ssl.backend.global;

import com.sun.org.apache.xpath.internal.operations.Bool;

import static hcs.dsl.ssl.backend.check.Checker.checkDate;

public class Global {

    private boolean realtime;

    private String start;
    private String end;

    public void setReaply(String start, String end) {
        checkDate(start);
        checkDate(end);

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
        return ",new Config("+ Boolean.toString(realtime) +",\""+start+"\"," +"\"" + end + "\")";
    }
}
