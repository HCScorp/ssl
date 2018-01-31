package ssl.dsl;

abstract class GroovyBaseScript extends Script {

    def source(String name) {
        println "SOURCE"
        [from: { address
            -> ((GroovySSLBinding)this.getBinding()).getGrovySSLModel().createSource(name, address)
        }]

    }


    def export(String name) {
        println "EXPORT"
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

