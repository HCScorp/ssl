package runtime.datasource;

import runtime.entity.SensorData;
import runtime.entity.SensorDataList;

import java.io.IOException;

public interface  SourceReader<V extends SensorData> {

    SensorDataList readContent(String address) throws IOException;


}
