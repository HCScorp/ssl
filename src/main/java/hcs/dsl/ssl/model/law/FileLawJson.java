package hcs.dsl.ssl.model.law;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import hcs.dsl.ssl.model.misc.Var;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileLawJson extends FileLaw {

    private JsonHeader jsonHeader;
    private ObjectMapper jsonMapper = new ObjectMapper();


    public FileLawJson(String lawName, String fileUri, String sensorName) {
        super(lawName, fileUri, sensorName, FileType.JSON);
    }

    public JsonHeader getJsonHeader() {
        return jsonHeader;
    }

    public void setJsonHeader(JsonHeader jsonHeader) {
        this.jsonHeader = jsonHeader;
    }

    @Override
    protected void fillData() throws IOException {
        List<Map<String, String>> entries = jsonMapper.readValue(file, new TypeReference<List<Map<String, String>>>() {});

        if (entries.isEmpty()) {
            return;
        }

        // Resolve keys for each sensor data to be gathered
        String keyTime = jsonHeader.nameOrDefaultOf(HeaderType.TIME);
        String keyName = jsonHeader.nameOrDefaultOf(HeaderType.NAME);
        String keyValue = jsonHeader.nameOrDefaultOf(HeaderType.VALUE);

        // Resolve value type
        Map<String, String> firstEntry = entries.get(0);
        String firstValue = firstEntry.get(keyValue);
        Var.Type typeData = resolveValueType(firstValue);
        setValType(typeData);

        // Parse JSON
        data = entries.stream()
                .filter(raw -> sensorName.equals(raw.get(keyName)))
                .map(raw -> new SensorData(parseTimestamp(raw.get(keyTime)), sensorName, typeData, raw.get(keyValue)))
                .collect(Collectors.toList());
    }
}
