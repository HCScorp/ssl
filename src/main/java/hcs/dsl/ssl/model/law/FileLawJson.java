package hcs.dsl.ssl.model.law;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hcs.dsl.ssl.model.misc.Var;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileLawJson extends FileLaw {

    private JsonHeader jsonHeader;
    private ObjectMapper jsonMapper = new ObjectMapper();


    public FileLawJson(String lawName, String fileUri) {
        super(lawName, fileUri, FileType.JSON);
    }

    public JsonHeader getJsonHeader() {
        return jsonHeader;
    }

    public void setJsonHeader(JsonHeader jsonHeader) {
        this.jsonHeader = jsonHeader;
    }

    @Override
    protected void fillData() throws IOException {
        List<Map<String, String>> input = jsonMapper.readValue(this.file, new TypeReference<List<Map<String, String>>>() {
        });

        Var.Type typeData;
        String keyValue = jsonHeader.getProperKey(Header.VALUE);
        String keyTime = jsonHeader.getProperKey(Header.TIME);
        String keyName = jsonHeader.getProperKey(Header.NAME);

        if (!data.isEmpty()) {
            // remove header
            typeData = this.findTypeValueFileContent(input.get(0).get(keyValue));
        } else {
            throw new NoSuchFieldError("No Value field detected");
        }
        this.setValType(typeData);

        input.forEach(mapRaw -> {
            SensorData sensorData = new SensorData();
            sensorData.setProperValue(typeData, mapRaw.get(keyValue));
            sensorData.setName(mapRaw.get(keyName));
            sensorData.setTime(Long.parseLong(mapRaw.get(keyTime)));
            this.data.add(sensorData);
        });
        data.sort((sensorData, t1) -> (int) (sensorData.getTime() - t1.getTime()));
    }
}
