package hcs.dsl.ssl.backend.sensor;

public class SourceJson extends SourceFile {

    private JsonHeader jsonHeader;

    public SourceJson(String fileName) {
        super(fileName, Type.JSON);
    }

    public JsonHeader getJsonHeader() {
        return jsonHeader;
    }

    public void setJsonHeader(JsonHeader jsonHeader) {
        this.jsonHeader = jsonHeader;
    }
}
