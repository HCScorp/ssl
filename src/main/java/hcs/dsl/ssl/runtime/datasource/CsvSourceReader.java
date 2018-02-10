package hcs.dsl.ssl.runtime.datasource;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import hcs.dsl.ssl.runtime.entity.SensorDataList;
import hcs.dsl.ssl.runtime.entity.SimpleListSensorData;

import java.io.File;
import java.io.IOException;

public class CsvSourceReader implements SourceReader {
    private CsvMapper mapper = new CsvMapper();
    private CsvSchema schema;


    CsvSourceReader() {
        schema = mapper.schemaFor(SimpleListSensorData.class).withHeader();
    }

    @Override
    public SensorDataList readContent(String address) throws IOException {
        return mapper.readValue(new File(address), SimpleListSensorData.class);
    }
}
