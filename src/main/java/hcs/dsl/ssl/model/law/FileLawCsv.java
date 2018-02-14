package hcs.dsl.ssl.model.law;

public class FileLawCsv extends FileLaw {

    private CsvHeader csvHeader;

    public FileLawCsv(String lawName, String fileUri) {
        super(lawName, fileUri, FileType.CSV);
    }

    public CsvHeader getCsvHeader() {
        return csvHeader;
    }

    public void setCsvHeader(CsvHeader csvHeader) {
        this.csvHeader = csvHeader;
    }

    @Override
    protected void fillData() {
        // TODO
    }
}
