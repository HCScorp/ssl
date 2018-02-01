package ssl.dsl;

import groovy.lang.Binding;
import kernel.datasource.DataSourceFactory;
import kernel.datasource.JsonSourceReader;
import kernel.entity.SensorData;
import kernel.entity.SensorDataList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroovySSLModel {

    private Binding binding;

    private SensorDataList sensorDataList;
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();


    GroovySSLModel(Binding binding) {
        this.binding = binding;
    }


    public void createSource(String type, String address) throws IOException {
        sensorDataList = DataSourceFactory.getDataSource(type).readContent(address);
    }

    // TODO : have to be changed : need to put value type in the dsl ?
    public void putNoise(int boundary) throws Exception {
        if (sensorDataList.getSensorDataList().isEmpty()){
            throw new Exception("Dataset can't be null");
        }
        sensorDataList.getSensorDataList()
                .stream().forEach(sensor -> {
            Integer value = ((SensorData<Integer>) sensor).getValue();
            ((SensorData<Integer>) sensor).setValue(value + new Random().nextInt(boundary));
        });
    }


    Object generateCode(String name) {
        return null;
    }
}
