


class Sensor_lumiOther extends NoisableSensor<Double> {

    public Sensor_lumiOther() {
        super("lumiOther",
                new Law_myRandomDouble(), 20)
    }
}


class Sensor_lumi extends NoisableSensor<Integer> {

    public Sensor_lumi() {
        super("lumi",
                new Law_myRandomList(), 10, new NoiseInteger(1, 2)
        )
    }
}


class Law_myRandom extends RandomLawIntervalInteger {
    public Law_myRandom() {
        super(1, 2);
    }
}


class Law_MarkovInteger extends MarkovLaw<Integer> {
    public Law_MarkovInteger() {
        super();

        addEdge(1, 0.1, 2);


        addEdge(2, 0.2, 1);


    }
}

class Law_myFunction extends FunctionLawDouble {
    public Law_myFunction() {
        super();
        addCase("ici", "la");
        addCase("labas", "where");
    }

}

class Law_myRandomList extends RandomLawArray<Integer> {
    public Law_myRandomList() {
        super(1, 2, 3, 4);
    }
}


class Law_myRandomListString extends RandomLawArray<String> {
    public Law_myRandomListString() {
        super("a", "b", "c", "d");
    }
}


class Law_myRandomDouble extends RandomLawIntervalDouble {
    public Law_myRandomDouble() {
        super(1.0, 2.0);
    }
}


class Law_MarkovString extends MarkovLaw<String> {
    public Law_MarkovString() {
        super();

        addEdge("lol", 0.1, "lolilol");


        addEdge("lolilol", 0.2, "lol");


    }
}

class Area_parking extends AreaType {
    public Area_parking() {
        super("parking",
                , new SensorGroup(3, Sensor_myRandomList)
                , new SensorGroup(4, Sensor_myRandomDouble)
        );
    }
}

class Exec_execParking extends Exec {
    public Exec_execParking() {
        super("execParking"
                , new Config(true, "09/12/2018 00:00", "09/12/2018 12:00")
                , new AreaInstance(new Area_parking, "parking1"), new AreaInstance(new Area_parking, "parking2")
        );
    }
}

