package hcs.dsl.ssl.model.law;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import hcs.dsl.ssl.model.misc.Var;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FileLawCsv extends FileLaw {

    private CsvHeader csvHeader;
    private CsvMapper objectMapper = new CsvMapper();


    public FileLawCsv(String lawName, String fileUri) {
        super(lawName, fileUri, FileType.CSV);
        objectMapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
    }

    public CsvHeader getCsvHeader() {
        return csvHeader;
    }

    public void setCsvHeader(CsvHeader csvHeader) {
        this.csvHeader = csvHeader;
    }

    @Override
    protected void fillData() throws IOException {

        MappingIterator<String[]> it = objectMapper.readerFor(String[].class).readValues(this.file);
        List<String[]> input = it.readAll();

        Var.Type typeData;
        int indexValue = csvHeader.indexValue(Header.VALUE);
        int indexTime = csvHeader.indexValue(Header.TIME);
        int indexName = csvHeader.indexValue(Header.NAME);

        if (!data.isEmpty()) {
            // remove header
            if (!patternLong.matcher(input.get(0)[indexTime]).find()) {
                data.remove(0);
            }
            typeData = this.findTypeValueFileContent(input.get(0)[csvHeader.indexValue(Header.VALUE)]);
        } else {
            throw new NoSuchFieldError("No Value field detected");
        }

        this.setValType(typeData);
        input.forEach(raw -> {
            SensorData sensorData = new SensorData();
            sensorData.setTime(Long.parseLong(raw[indexTime]));
            sensorData.setName(raw[indexName]);
            sensorData.setProperValue(typeData, raw[indexValue]);
            data.add(sensorData);
        });
        data.sort((sensorData, t1) -> (int) (sensorData.getTime() - t1.getTime()));
    }
}
