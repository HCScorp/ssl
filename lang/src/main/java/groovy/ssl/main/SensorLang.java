package ssl.main;


import ssl.dsl.GroovySensorDSL;

import java.io.File;

public class SensorLang {

    public static void main(String[] args) {
        System.out.println("Start the DSL");
        GroovySensorDSL dsl = new GroovySensorDSL();
        if(args.length > 0) {
            dsl.eval(new File(args[0]));
        } else {
            System.out.println("/!\\ Missing arg: Please specify the path to a Groovy script file to execute");
        }
    }
}
