package runtime.datasource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import runtime.entity.SensorDataList;
import runtime.entity.SimpleListSensorData;

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
