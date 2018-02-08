package runtime.sensor;

import runtime.noise.Noise;

public class NoisableSensor<T extends Number> extends Sensor<T> {
    private Noise<T> noise;

    @Override
    public T produceValue(long timestamp) {
        return noise.apply(super.produceValue(timestamp));
    }
}
