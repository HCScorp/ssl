package kernel.datasource;

import com.fasterxml.jackson.databind.ObjectMapper;
import kernel.entity.SensorDataList;
import kernel.entity.SimpleListSensorData;
import org.apache.commons.validator.UrlValidator;

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
