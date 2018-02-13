package hcs.dsl.ssl.runtime.sensor;

import hcs.dsl.ssl.runtime.noise.Noise;
import hcs.dsl.ssl.runtime.source.Source;

public class NoisableSensor<T extends Number> extends Sensor<T> {

    private Noise<T> noise;

    public NoisableSensor(String name, Source<T> source, long period, Noise<T> noise) {
        super(name, source, period);
        this.noise = noise;
    }

    public void setNoise(Noise<T> noise) {
        this.noise = noise;
    }

    @Override
    public T produceValue(long timestamp) {
        if (noise != null) {
            return noise.apply(super.produceValue(timestamp));
        }

        return super.produceValue(timestamp);
    }
}
