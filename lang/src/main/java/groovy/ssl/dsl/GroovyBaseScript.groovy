package ssl.dsl;

abstract class GroovyBaseScript extends Script {


    def source(String type) {

        [from: { address ->
            [name: { text ->
                ((GroovySSLBinding) this.getBinding()).getGrovySSLModel().createSource(type, address, text)
            }]
        }]

    }

    def make_noise(int bound) {
        [name: { name ->
            ((GroovySSLBinding) this.getBinding()).getGrovySSLModel().putNoise(bound, name)

        }]
    }

    def shift(String date) {
        [name: {name ->
            ((GroovySSLBinding) this.getBinding()).getGrovySSLModel().changeOffset(date, name)

        }
        ]
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

