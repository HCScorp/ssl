package runtime.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.validator.UrlValidator;
import runtime.entity.SimpleListSensorData;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonFileSource<T extends Serializable> extends FileSource<T> {
    public JsonFileSource(String uri) {
        super(uri); // TODO



    }

    @Override
    public T produceValue(long timestamp) {
        return null; // TODO
    }
}
