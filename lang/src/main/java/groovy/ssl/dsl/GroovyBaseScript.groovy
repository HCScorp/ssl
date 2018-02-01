package ssl.dsl;

abstract class GroovyBaseScript extends Script {

    def source(String name) {
        [from: { address
            -> ((GroovySSLBinding)this.getBinding()).getGrovySSLModel().createSource(name, address)
                ["make_noise": { noise ->
                    ((GroovySSLBinding)this.getBinding()).getGrovySSLModel().putNoise(noise)}
                ]}]

    }


    def export(String name) {
        println(((GroovySSLBinding) this.getBinding()).getGrovySSLModel().generateCode(name).toString())
    }


    abstract void scriptBody()
    int count = 0

    def  run() {
            if(count == 0) {
                count++
                scriptBody()
            } else {
                println( "Run method is disabled");
            }
        }
    }

