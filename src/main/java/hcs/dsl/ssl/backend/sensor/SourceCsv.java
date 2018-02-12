package hcs.dsl.ssl.backend.sensor;

public class SourceCsv extends SourceFile {

    private CsvHeader csvHeader;

    public SourceCsv(String fileName) {
        super(fileName, Type.CSV);
    }

    public CsvHeader getCsvHeader() {
        return csvHeader;
    }

    public void setCsvHeader(CsvHeader csvHeader) {
        this.csvHeader = csvHeader;
    }
}
