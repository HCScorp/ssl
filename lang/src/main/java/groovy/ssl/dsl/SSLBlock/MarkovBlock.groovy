package ssl.dsl.SSLBlock

import ssl.dsl.GroovySSLBinding

class MarkovBlock extends Block {

    MarkovBlock(GroovySSLBinding binding, String name) {
        super(binding, name)
    }


    def state(name) {
        [with: { val ->
            [give: { state ->
                print("with" + val + "give " + state)
            }]
        }]

    }

}
