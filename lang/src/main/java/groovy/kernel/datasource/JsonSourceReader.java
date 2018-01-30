package kernel.datasource;

import com.fasterxml.jackson.databind.ObjectMapper;
import kernel.entity.SensorDataList;
import kernel.entity.SimpleListSensorData;
import org.apache.commons.validator.UrlValidator;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JsonSourceReader<V, T extends SensorDataList> implements SourceReader {

    private ObjectMapper mapper = new ObjectMapper();

    public SensorDataList readContent(String address) throws IOException {
        System.out.println("Read the content of the adress.");
        UrlValidator urlValidator = new UrlValidator();
        Object input = null;
        if (urlValidator.isValid(address)) {
            return mapper.readValue(new URL(address), SimpleListSensorData.class);
        } else {
            return mapper.readValue(new File(address), SimpleListSensorData.class);
        }
    }
}
