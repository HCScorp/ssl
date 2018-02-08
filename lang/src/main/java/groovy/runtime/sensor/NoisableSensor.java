package runtime.sensor;

import runtime.noise.Noise;
import runtime.period.Period;
import runtime.source.Source;

public class NoisableSensor<T extends Number> extends Sensor<T> {

    private Noise<T> noise;

    public NoisableSensor(String name, Source<T> source, Period period, Noise<T> noise) {
        super(name, source, period);
        this.noise = noise;
    }

    @Override
    public T produceValue(long timestamp) {
        return noise.apply(super.produceValue(timestamp));
    }
}
