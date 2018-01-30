package kernel.datasource;

import kernel.entity.SensorData;
import kernel.entity.SensorDataList;

import java.io.IOException;

public interface  SourceReader<V extends SensorData> {

    SensorDataList readContent(String address) throws IOException;


}
