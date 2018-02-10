package hcs.dsl.ssl.runtime.datasource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.validator.UrlValidator;
import hcs.dsl.ssl.runtime.entity.SensorDataList;
import hcs.dsl.ssl.runtime.entity.SimpleListSensorData;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JsonSourceReader implements SourceReader {

    private ObjectMapper mapper = new ObjectMapper();

    public SensorDataList readContent(String address) throws IOException {

        UrlValidator urlValidator = new UrlValidator();
        if (urlValidator.isValid(address)) {
            return mapper.readValue(new URL(address), SimpleListSensorData.class);
        } else {
            return mapper.readValue(new File(address), SimpleListSensorData.class);
        }
    }
}
