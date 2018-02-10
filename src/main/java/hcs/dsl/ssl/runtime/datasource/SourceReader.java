package hcs.dsl.ssl.runtime.datasource;

import hcs.dsl.ssl.runtime.entity.SensorData;
import hcs.dsl.ssl.runtime.entity.SensorDataList;

import java.io.IOException;

public interface  SourceReader<V extends SensorData> {

    SensorDataList readContent(String address) throws IOException;


}
