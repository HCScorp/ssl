package ssl.dsl.util

class Interval {
    List interval
    String name

    Interval(String name) {
        this.name = name
    }

    def getAt(List args){
        interval = args
    }

    @Override
    public String toString() {
        return "Interval{" +
                "interval=" + interval +
                ", name='" + name + '\'' +
                '}';
    }
}
