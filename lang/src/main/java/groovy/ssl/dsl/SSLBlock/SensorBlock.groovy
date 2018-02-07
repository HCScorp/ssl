package ssl.dsl.SSLBlock

import ssl.dsl.GroovySSLBinding;

class SensorBlock extends Block {
    GroovySSLBinding binding
    String nameBlock

    SensorBlock(GroovySSLBinding binding, String name) {
        super(binding, name)
    }

    def source(source){
        println("source : " + source)
        // detect if source is an existed law
        [with: { function, args ->
            println(function + " " + args)
        }]
    }

    def noise( maximus, minus ){
        println(maximus + " " +  minus)
    }

    def offset(offsetValue){
        println(offsetValue)
    }

    def test(Closure cl){
        def res = cl()
        println(res)
    }

}
