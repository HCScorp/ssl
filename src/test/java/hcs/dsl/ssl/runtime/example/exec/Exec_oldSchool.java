package hcs.dsl.ssl.runtime.example.exec;

import hcs.dsl.ssl.runtime.area.AreaInstance;
import hcs.dsl.ssl.runtime.example.areas.Area_bureau;
import hcs.dsl.ssl.runtime.example.areas.Area_parking;
import hcs.dsl.ssl.runtime.example.areas.Area_salleDeClasse;
import hcs.dsl.ssl.runtime.exec.Config;
import hcs.dsl.ssl.runtime.exec.Exec;

public class Exec_oldSchool extends Exec {

    public Exec_oldSchool() {
        super("oldSchoold",
                new Config(false, null, "09/12/2017 00:00", "10/12/2017 00:00"),

                new AreaInstance(new Area_parking(), "P1"),
                new AreaInstance(new Area_parking(), "P2"),

                new AreaInstance(new Area_salleDeClasse(), "C1"),
                new AreaInstance(new Area_salleDeClasse(), "C2"),
                new AreaInstance(new Area_salleDeClasse(), "C3"),

                new AreaInstance(new Area_bureau(), "B1"),
                new AreaInstance(new Area_bureau(), "B2"),
                new AreaInstance(new Area_bureau(), "B3"),
                new AreaInstance(new Area_bureau(), "B4")
        );
    }
}
