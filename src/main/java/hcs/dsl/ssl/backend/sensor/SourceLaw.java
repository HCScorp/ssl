package hcs.dsl.ssl.backend.sensor;

public class SourceLaw extends Source {

    private final String lawRef;

    public SourceLaw(String lawRef) {
        this.lawRef = lawRef;
    }

    public String getLawRef() {
        return lawRef;
    }
}
