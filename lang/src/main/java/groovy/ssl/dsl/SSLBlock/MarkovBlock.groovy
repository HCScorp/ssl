package ssl.dsl.SSLBlock

import ssl.dsl.GroovySSLBinding

class MarkovBlock extends Block {

    // TODO: remove with proper backend.
    String initialState
    String secondState
    Double probability

    MarkovBlock(GroovySSLBinding binding, String name) {
        super(binding, name)
    }


    def state(MarkovBlock name) {
        println(name.initialState + " " + name.secondState + " " + name.probability )
        initialState = null
        secondState = null
    }

    def methodMissing(String name, args){
        println(name + args)
        println("method")
    }

    def rightShift(Object arg){
        if (arg instanceof BigDecimal){
            println("Add the probability")
            probability = (Double) arg
        }
        return this

    }

    // to be replaced...
    def propertyMissing(String name, String args){
        if (initialState == null){
            initialState = name
        }
        else {
            secondState = name
        }
        return this
    }


}
