package hcs.dsl.ssl.runtime.exec;

import hcs.dsl.ssl.runtime.example.exec.Exec_oldSchool;
import org.junit.Test;

public class ExecTest {

    @Test
    public void execOldSchoolTest() {
        Exec_oldSchool e = new Exec_oldSchool();
        e.run();
    }
}
