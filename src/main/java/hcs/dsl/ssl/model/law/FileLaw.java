package hcs.dsl.ssl.model.law;

import com.google.common.primitives.Doubles;
import hcs.dsl.ssl.model.misc.Interval;
import hcs.dsl.ssl.model.misc.Var;
import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
import org.apache.commons.validator.UrlValidator;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class FileLaw extends Law {

    public enum FileLocation {
        LOCAL,
        DISTANT
    }

    public enum FileType {
        JSON,
        CSV
    }

    protected Pattern patternInteger = Pattern.compile("^-?\\\\d+$");
    protected Pattern patternDouble = Pattern.compile("\\d+\\.\\d+");
    protected Pattern patternBoolean = Pattern.compile("[true|false|True|False]");
    protected Pattern patternLong = Pattern.compile("^-?\\d{1,19}$");


    private Var.Type valType;

    private String fileUri;
    private FileType fileType;

    private FileLocation fileLocation;
    private Interpolation interpolation;

    protected File file;
    protected List<SensorData> data; // TODO to fill

    protected FileLaw(String lawName, String fileUri, FileType fileType) {
        super(lawName, Type.FILE);
        System.out.println("file URI ::" + fileUri);
        this.fileUri = fileUri;
        this.fileType = fileType;

        // TODO fetch data, then when you check if there is an interpolation or not to determine what to generate
        fetchFile();
    }

    private void fetchFile() {
        System.out.println(fileLocation);
        if (FileLocation.LOCAL.equals(fileLocation)) {
            fetchLocalFile();
        } else if (FileLocation.DISTANT.equals(fileLocation)) {
            fetchDistantFile();
        } else {
            throw new RuntimeException("file location of law '" + getName() + "' must be defined");
        }
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
            throw new RuntimeException(e);
        }
    }

    protected abstract void fillData() throws IOException; // TODO load data according to header (or default!!)

    public void setValType(Var.Type valType) { // TODO to use at the end of the computation
        this.valType = valType;
    }

    @Override
    public Var.Type getValType() {
        return valType;
    }

    public String getFileUri() {
        return fileUri;
    }

    public FileType getFileType() {
        return fileType;
    }

    public FileLocation getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = FileLocation.valueOf(fileLocation.toUpperCase());
    }

    public Interpolation getInterpolation() {
        return interpolation;
    }

    public void setInterpolation(Interpolation interpolation)  {
        this.interpolation = interpolation;
        if (this.valType == Var.Type.Boolean || this.valType == Var.Type.String){
            // TODO : throw exception
            System.out.println("error boolean | String can't be interpolate.");
        }

        if (interpolation.getRestriction() != null){
            Interval interval = interpolation.getRestriction();
            this.data.removeIf(sensorData ->
                    sensorData.getTime() < interval.min.longValue()  || sensorData.getTime() > interval.max.longValue());
        }
        else {
            if (this.valType == Var.Type.Integer){
                interpolation.setRestriction(new Interval<>(Interval.Type.Integer,(int) this.data.get(0).getTime(),
                        (int) this.data.get( this.data.size() -1).getTime()));
            }
            else {
                interpolation.setRestriction(new Interval<>(Interval.Type.Double,(double) this.data.get(0).getTime(),
                        (double) this.data.get( this.data.size() -1).getTime()));
            }
        }
        double[] y;
        double[] x = this.data.stream().mapToDouble(sensorData -> (double) sensorData.getTime()).toArray();
        if (valType == Var.Type.Integer){
            y = this.data.stream().mapToDouble(sensorData -> (double) sensorData.getValueInteger()).toArray();
        }
        else {
            y = this.data.stream().mapToDouble(SensorData::getValueDouble).toArray();
        }

        PolynomialFunctionLagrangeForm polynomialFunctionLagrangeForm = new PolynomialFunctionLagrangeForm(x, y);
        this.interpolation.setCoefPolynome(Doubles.asList(polynomialFunctionLagrangeForm.getCoefficients()));
    }

    public Var.Type findTypeValueFileContent(String rawValue) {
        Var.Type type = Var.Type.String;
        if (patternBoolean.matcher(rawValue).find()) type = Var.Type.Boolean;
        else if (patternDouble.matcher(rawValue).find()) type = Var.Type.Double;
        else if (patternInteger.matcher(rawValue).find()) type = Var.Type.Integer;
        return type;
    }

}

