package hcs.dsl.ssl.runtime.exec;

import hcs.dsl.ssl.runtime.example.exec.Exec_oldSchool;
import hcs.dsl.ssl.runtime.example.exec.Exec_oldSchoolRealtime;
import org.junit.Ignore;
import org.junit.Test;

public class ExecTest {

    @Ignore
    public void execOldSchoolTest() {
        Exec_oldSchool e = new Exec_oldSchool();
        e.run();
    }

    @Ignore
    public void execOldSchoolRealtimeTest() {
        Exec_oldSchoolRealtime e = new Exec_oldSchoolRealtime();
        e.run();
    }
}
