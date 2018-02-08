package ssl.dsl.SSLBlock

import ssl.dsl.GroovySSLBinding
import ssl.dsl.util.Interval

class RandomBlock extends  Block {

    List<Interval> interval = new ArrayList<>()


    RandomBlock(GroovySSLBinding binding, String name) {
        super(binding, name)
    }


    def getAt(List interval){
        this.interval = interval
    }

    def values_in(Integer...a){
        
    }

    def propertyMissing(String name, String args){
        println("name : " + name + " args : " + args)
        Interval data = new Interval()
        data.name = "name"
        interval.add(data)
        switch (name){
            case "values_in":
                return data
            case "frequency":
                return data
            default:
                println("Bad keyword...")
        }

        return null
    }

}
