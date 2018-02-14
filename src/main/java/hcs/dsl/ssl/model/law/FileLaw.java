package hcs.dsl.ssl.model.law;

import hcs.dsl.ssl.model.misc.Var;
import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.UrlValidator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public abstract class FileLaw extends Law {

    public enum FileLocation {
        LOCAL,
        DISTANT
    }

    public enum FileType {
        JSON,
        CSV
    }

    private Var.Type valType;

    private String fileUri;
    private FileType fileType;

    private FileLocation fileLocation;
    private Interpolation interpolation;

    protected File file;
    protected List<SensorData> data; // TODO to fill

    protected FileLaw(String lawName, String fileUri, FileType fileType) {
        super(lawName, Type.FILE);
        this.fileUri = fileUri;
        this.fileType = fileType;

        // TODO fetch data, then when you check if there is an interpolation or not to determine what to generate
        fetchFile();
    }

    private void fetchFile() {
        if (FileLocation.DISTANT.equals(fileLocation)) {
            fetchLocalFile();
        } else if (FileLocation.LOCAL.equals(fileLocation)) {
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

    protected abstract void fillData(); // TODO load data according to header (or default!!)

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

    public void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
        // TODO build INTERPOLATION (use the interpolation object)
    }
}
