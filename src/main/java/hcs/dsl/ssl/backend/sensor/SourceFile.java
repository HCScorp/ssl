package hcs.dsl.ssl.backend.sensor;

public class SourceFile extends Source {

    public enum Location { // TODO put in the right place
        LOCAL,
        DISTANT
    }

    public enum Type {
        JSON,
        CSV
    }

    private final String fileName;
    private final Type fileType;

    private String location;
    private Interpolation interpolation;

    public SourceFile(String fileName, Type fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public Type getFileType() {
        return fileType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Interpolation getInterpolation() {
        return interpolation;
    }

    public void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
    }
}
