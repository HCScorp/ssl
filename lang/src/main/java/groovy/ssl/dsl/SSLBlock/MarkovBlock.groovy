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

    def rightShift(Object arg){
        if (arg instanceof BigDecimal){
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
            initialState = null
            secondState = null
        }
        return this
    }


}
