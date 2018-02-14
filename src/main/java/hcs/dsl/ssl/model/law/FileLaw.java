package hcs.dsl.ssl.model.law;

import hcs.dsl.ssl.model.misc.Var;

public class FileLaw extends Law {

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

    private FileLocation location;
    private Interpolation interpolation;

    public FileLaw(String lawName, String fileUri, FileType fileType) {
        super(lawName, Type.FILE);
        this.fileUri = fileUri;
        this.fileType = fileType;

        // TODO fetch data, then when you check if there is an interpolation or not to determine what to generate
    }

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

    public FileLocation getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = FileLocation.valueOf(location.toUpperCase());
    }

    public Interpolation getInterpolation() {
        return interpolation;
    }

    public void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
        // TODO build INTERPOLATION (use the interpolation object)
    }
}
