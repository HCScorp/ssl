package ssl.dsl.SSLBlock

import ssl.dsl.GroovySSLBinding;

class SensorBlock extends Block {
    GroovySSLBinding binding
    String nameBlock

    SensorBlock(GroovySSLBinding binding, String name) {
        super(binding, name)
    }

    def source( String source){
        // detect if source is an existed law
        [with: { function, args ->
            println(function + " " + args)
        }]
    }

    def noise( Integer maximus, Integer minus ){
        println(maximus + " " +  minus)
    }

    def offset(String offsetValue){
        println(offsetValue)
    }



}
