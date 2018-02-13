
            
    

            class Sensor_capteurTempSalle extends NoisableSensor<Double>{

        public Sensor_capteurTempSalle() {
        super("capteurTempSalle",
                new Law_temperatureIntervalle(),100000                , new NoiseDouble(-0.05,0.02 )
                )
    }}
        
    

            class Sensor_capteurTempBureau extends NoisableSensor<Integer>{

        public Sensor_capteurTempBureau() {
        super("capteurTempBureau",
                new Law_temperatureEnsemble(),60000                )
    }}
        
    

            class Sensor_capteurLumiFenetre extends Sensor<String>{

        public Sensor_capteurLumiFenetre() {
        super("capteurLumiFenetre",
                new Law_lumiDehors(),300000                )
    }}
    

            class Law_lumiDehors extends MarkovLaw<String> {
        public Law_lumiDehors (){
        super();
            }
        }
                                        class Law_temperatureIntervalle extends RandomLawIntervalDouble {
                        public Law_temperatureIntervalle (){super(18.0,24.5);}
            }
        

                                    class Law_temperatureEnsemble extends RandomLawArray<Integer> {
                public Law_temperatureEnsemble (){ super(18, 12, 42, 24);
                }}

                

                    class Law_polyMilieuJournee extends FunctionLawDouble {
        public Law_polyMilieuJournee() {
            super();
                            addCase("x%(3600*24) < 32200", "4");
                            addCase("x%(3600*24) > 32200", "abs(-(2*x^2) + 5*x - 1)");
                            addCase("x%(3600*24) = 32200", "0");
            }

        }

                class Law_simpleCarre extends FunctionLawDouble {
        public Law_simpleCarre() {
            super();
                            addCase("x", "x^2");
            }

        }

                                class Law_presenceCoca extends RandomLawArray<String> {
                public Law_presenceCoca (){ super("yep","nop");
                }}

                

        
class Area_parking extends AreaType {
public Area_parking() {
    super("parking",
            , new SensorGroup(2,Sensor_capteurNombreVoiture.class, new NoiseInteger(1,6))
    );
}}

class Area_salleDeClasse extends AreaType {
public Area_salleDeClasse() {
    super("salleDeClasse",
            , new SensorGroup(3,Sensor_capteurLumiFenetre.class)
            , new SensorGroup(1,Sensor_capteurTempSalle.class, new NoiseDouble(1.0,4.0))
    );
}}

class Area_bureau extends AreaType {
public Area_bureau() {
    super("bureau",
            , new SensorGroup(2,Sensor_capteurTempBureau.class)
    );
}}

class Exec_oldSchool extends Exec {
    public Exec_oldSchool () {
    super("oldSchool"
    ,new Config(false,"09/12/2017 00:00","10/12/2017 00:00")
            ,new AreaInstance(new Area_parking(),"P1"),new AreaInstance(new Area_parking(),"P2")
            ,new AreaInstance(new Area_salleDeClasse(),"C1"),new AreaInstance(new Area_salleDeClasse(),"C2")
            ,new AreaInstance(new Area_bureau(),"B1"),new AreaInstance(new Area_bureau(),"B2"),new AreaInstance(new Area_bureau(),"B3"),new AreaInstance(new Area_bureau(),"B4")
    );
    }
    }

class Exec_newSchool extends Exec {
    public Exec_newSchool () {
    super("newSchool"
    ,new Config(false,"09/12/2017 00:00","10/12/2017 00:00")
            ,new AreaInstance(new Area_parking(),"P3")
            ,new AreaInstance(new Area_salleDeClasse(),"C3"),new AreaInstance(new Area_salleDeClasse(),"C4")
    );
    }
    }

