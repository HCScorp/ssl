package ssl.dsl;

abstract class GroovyBaseScript extends Script {

    def source(String name) {
        println("source ")
        [from: { address ->
            ((GroovySSLBinding) this.getBinding()).getGrovySSLModel().createSource(name, address)
        }]
    }

    def make_noise(int bound){
        ((GroovySSLBinding) this.getBinding()).getGrovySSLModel().putNoise(bound)
    }

    def shitf(String date){
        ((GroovySSLBinding) this.getBinding()).getGrovySSLModel().changeOffset(date)
    }

    def export(String name) {
        println "Export the code."
        println(((GroovySSLBinding) this.getBinding()).getGrovySSLModel().generateCode(name).toString())
    }


    abstract void scriptBody()
    int count = 0

    def run() {
        if (count == 0) {
            count++
            scriptBody()
        } else {
            println("Run method is disabled");
        }
    }
}

