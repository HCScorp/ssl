package hcs.dsl.ssl.antlr;

import hcs.dsl.ssl.antlr.grammar.*;
import hcs.dsl.ssl.backend.Model;

import java.util.HashMap;
import java.util.Map;

public class ModelBuilder extends SSLBaseListener {

    /********************
     ** Business Logic **
     ********************/

    private Model model = null;
    private boolean built = false;

    public Model retrieve() {
        if (built) {
            return model;
        }
        throw new RuntimeException("Cannot retrieve a model that was not created!");
    }

    /*******************
     ** Symbol tables **
     *******************/

    private Map<String, String> todo = new HashMap<>();

    /**************************
     ** Listening mechanisms **
     **************************/
    
    

}

