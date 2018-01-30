package ssl.dsl;

abstract class GroovyBaseScript extends Script {

    def source(String name) {
        [source: {((GroovySSLBinding)this.getBinding()).getGrovySSLModel().createSource(name) }]
    }


    def export(String name) {
        println(((GroovySSLBinding) this.getBinding()).getGrovySSLModel().generateCode(name).toString())
    }


    abstract void scriptBody();
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

