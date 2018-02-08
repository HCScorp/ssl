package ssl.dsl

import ssl.dsl.SSLBlock.MarkovBlock
import ssl.dsl.SSLBlock.RandomBlock
import ssl.dsl.SSLBlock.SensorBlock;

abstract class GroovyBaseScript extends Script {

    def sensor(name, Closure closure){
        SensorBlock block = new SensorBlock((GroovySSLBinding) this.binding, name)
        closure.delegate = block
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }


    def markov_law(name, Closure closure){
        println("markov Law")

        MarkovBlock markovBlock = new MarkovBlock((GroovySSLBinding) this.binding, name)
        closure.delegate = markovBlock
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }

    def random_law(name, Closure closure){
        println("random Law")
        RandomBlock randomBlock = new RandomBlock((GroovySSLBinding) this.binding, name)
        closure.delegate = randomBlock
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
        println(randomBlock.interval)
    }

    def propertyMissing(String name) {
        return new MarkovBlock((GroovySSLBinding) this.binding, name)
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

