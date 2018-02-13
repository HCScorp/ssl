public class Sensor_lumi extends Sensor<String>{
    public Sensor_lumi() {
        super("lumi" )
    }

    }
                            public class Law_myRandomList extends RandomLawArray<Integer> {
                public Law_myRandomList (){ super(1, 2, 3, 4)
                }

                        }

        


                                        public class Law_myRandom extends RandomLawIntervalInteger {
                        public Law_myRandom (){super(1,2);}
            }


                }

        


            public class Law_MarkovInteger extends MarkovLaw<Integer> {
        public Law_MarkovInteger (){
        super();
    
                    addEdge( 1, 0.1, 2 );
        

        
                    addEdge( 2, 0.2, 1 );
        

                }
        }
    


            public class Law_myFunction extends FunctionLawDouble {
        public Law_myFunction() {
            super();
            
                addCase("ici", "la");
            
                addCase("labas", "where");
            
        }

    


                            public class Law_myRandomListString extends RandomLawArray<String> {
                public Law_myRandomListString (){ super("a","b","c","d")
                }

                        }

        


            public class Law_MarkovString extends MarkovLaw<String> {
        public Law_MarkovString (){
        super();
    
                    addEdge( "lol", 0.1, "lolilol" );
        

        
                    addEdge( "lolilol", 0.2, "lol" );
        

                }
        }
    


