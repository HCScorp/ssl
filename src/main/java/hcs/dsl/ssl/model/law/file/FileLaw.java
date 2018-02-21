package hcs.dsl.ssl.model.law.file;

import com.google.common.primitives.Doubles;
import hcs.dsl.ssl.model.law.Law;
import hcs.dsl.ssl.model.misc.ValType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
import org.apache.commons.validator.UrlValidator;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import static hcs.dsl.ssl.model.misc.CheckHelper.*;

public abstract class FileLaw extends Law {

    public enum FileLocation {
        LOCAL,
        DISTANT
    }

    public enum FileType {
        JSON,
        CSV
    }

    private ValType valType;

    protected final String fileUri;
    protected final String sensorName;
    protected final FileType fileType;

    private FileLocation fileLocation;
    private Interpolation interpolation;

    protected File file;
    protected List<SensorData> data;

    private long minTimestamp;
    private long maxTimestamp;

    protected FileLaw(String lawName, String fileUri, String sensorName, FileType fileType) {
        super(lawName, Type.FILE);
        this.fileUri = fileUri;
        this.sensorName = sensorName;
        this.fileType = fileType;
    }

    public void setValType(ValType valType) {
        this.valType = valType;
    }

    @Override
    public ValType getValType() {
        return valType;
    }

    public String getFileUri() {
        return fileUri;
    }

    public List<SensorData> getData() {
        return data;
    }


    public String getSensorName() {
        return sensorName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public long getMinTimestamp() {
        return minTimestamp;
    }

    public long getMaxTimestamp() {
        return maxTimestamp;
    }

    public File getFile() {
        return file;
    }

    //////////////////
    //// Fetching ////
    //////////////////

    public FileLocation getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = FileLocation.valueOf(fileLocation.toUpperCase());

        try {
            fetchFile();
        } catch (IOException e) {
            throw new IllegalArgumentException("error when fetching file '" + getFileUri() + "' : ", e);
        }
    }

    protected static long parseTimestamp(String raw) {
        if (!PATTERN_TIMESTAMP.matcher(raw).matches()) {
            throw new IllegalArgumentException("timestamp '" + raw + "' must be in second and composed by at least and at most 10 digits");
        }

        return Long.parseLong(raw);
    }


    private void fetchFile() throws IOException {
        if (FileLocation.LOCAL.equals(fileLocation)) {
            fetchLocalFile();
        } else if (FileLocation.DISTANT.equals(fileLocation)) {
            fetchDistantFile();
        } else {
            throw new RuntimeException("file location of law '" + getName() + "' must be defined");
        }

        fillData();

        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("file '" + fileUri + "' contains no entry");
        }

        Collections.sort(data);
        minTimestamp = data.get(0).getTimestamp();
        maxTimestamp = data.get(data.size() - 1).getTimestamp();
    }

    private void fetchLocalFile() {
        this.file = new File(fileUri);
    }

    private void fetchDistantFile() {
        try {
            UrlValidator urlValidator = new UrlValidator();
            if (urlValidator.isValid(fileUri)) {
                this.file = File.createTempFile("datasource", ".tmp");
                FileUtils.copyURLToFile(new URL(fileUri), file);
            } else {
                throw new RuntimeException("law '" + getName() + "' must have a valid URL");
            }
        } catch (IOException e) {
            throw new RuntimeException("error when downloading file " + fileUri + " for law: " + getName(), e);
        }
    }

    protected abstract void fillData() throws IOException;


    ///////////////////////
    //// Interpolation ////
    ///////////////////////

    public Interpolation getInterpolation() {
        return interpolation;
    }

    public void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
        computeInterpolation();
    }

    protected ValType resolveValueType(String raw) {
        return PATTERN_INTEGER.matcher(raw).matches() ? ValType.Integer
                : PATTERN_DOUBLE.matcher(raw).matches() ? ValType.Double
                : PATTERN_BOOLEAN.matcher(raw).matches() ? ValType.Boolean
                : ValType.String;
    }

    private void computeInterpolation() {
        if (valType == ValType.Boolean || valType == ValType.String) {
            throw new IllegalArgumentException("cannot interpolate data values of type " + valType + " for file '" + fileUri + "'");
        }

        double[] y;
        double[] x = data.stream().mapToDouble(sensorData -> (double) sensorData.getTimestamp()).toArray();
        if (valType == ValType.Integer) {
            y = data.stream().mapToDouble(sensorData -> (double) sensorData.getInteger()).toArray();
        } else if (valType == ValType.Double) {
            y = data.stream().mapToDouble(SensorData::getDouble).toArray();
        } else {
            throw new IllegalArgumentException("cannot interpolate data values of type " + valType + " for file '" + fileUri + "'");
        }

        PolynomialFunctionLagrangeForm polynomialFunctionLagrangeForm = new PolynomialFunctionLagrangeForm(x, y);
        interpolation.setCoefPolynome(Doubles.asList(polynomialFunctionLagrangeForm.getCoefficients()));
    }
}

