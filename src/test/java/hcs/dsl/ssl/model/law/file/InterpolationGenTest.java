package hcs.dsl.ssl.model.law.file;

import hcs.dsl.ssl.model.misc.ValType;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class InterpolationGenTest {

    class FakeFileData extends FileLaw {

        protected FakeFileData() {
            super("fakeLaw", "emptyUri", "fakeName", FileType.CSV);
            fillData();
        }

        @Override
        protected void fillData() {
            this.data = Arrays.asList(
                    data(1514764800, 1),
                    data(1514764842, 1),
                    data(1514854799, 2),
                    data(1514858399, 3),
                    data(1514861999, 5),
                    data(1514865599, 8),
                    data(1514869199, 12),
                    data(1514872799, 15),
                    data(1514876399, 19),
                    data(1514879999, 22),
                    data(1514883599, 24),
                    data(1514887199, 25),
                    data(1514890799, 22),
                    data(1514894399, 20),
                    data(1514897999, 15),
                    data(1514901599, 11),
                    data(1514905199, 8),
                    data(1514908799, 7),
                    data(1514912399, 5),
                    data(1514915999, 4),
                    data(1514919599, 3),
                    data(1514923199, 2),
                    data(1514926799, 1),
                    data(1514930399, 1),
                    data(1514933999, 1),
                    data(1514937599, 1)
            );

            this.minTimestamp = 1514764800;
            this.maxTimestamp = 1514937599;
            this.setValType(ValType.Integer);
        }
    }

    private static SensorData data(long timestamp, int value) {
        return new SensorData(timestamp, "s", ValType.Integer, Integer.toString(value));
    }

    @Test
    public void interpolationTest() throws Exception {
        FakeFileData ffd = new FakeFileData();

        double[] x = ffd.data.stream()
                .map(sd -> sd.getTimestamp() - ffd.minTimestamp)
                .mapToDouble(Long::doubleValue).toArray();
        double[] y = ffd.data.stream().mapToDouble(sd -> sd.getInteger().doubleValue()).toArray();

        PolynomialSplineFunction psf = new LinearInterpolator().interpolate(x, y);

        PolynomialFunction[] poly = new PolynomialFunction[psf.getPolynomials().length];
        int i = 0;
        for (PolynomialFunction pf : psf.getPolynomials()) {
            poly[i++] = new PolynomialFunction(pf.getCoefficients());
        }
        PolynomialSplineFunction psf2 = new PolynomialSplineFunction(psf.getKnots(), poly);

        // Check that the two interpolation behave the same way
        for (long j = 0; j < 172700; j++) {
            assertEquals(psf.value((double) j), psf2.value((double) j), 0.0001);
        }
    }
}
