package hcs.dsl.ssl.model.law;

public class FileLawJson extends FileLaw {

    private JsonHeader jsonHeader;

    public FileLawJson(String lawName, String fileUri) {
        super(lawName, fileUri, FileType.JSON);
    }

    public JsonHeader getJsonHeader() {
        return jsonHeader;
    }

    public void setJsonHeader(JsonHeader jsonHeader) {
        this.jsonHeader = jsonHeader;
    }

    @Override
    protected void fillData() {
        // TODO
    }
}
