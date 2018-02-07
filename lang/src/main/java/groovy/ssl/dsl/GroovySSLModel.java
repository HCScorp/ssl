package ssl.dsl;

import groovy.lang.Binding;
import kernel.datasource.DataSourceFactory;
import kernel.datasource.JsonSourceReader;
import kernel.entity.SensorData;
import kernel.entity.SensorDataList;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GroovySSLModel {

    private Binding binding;

    private Map<String, SensorDataList> sensorDataList;
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    GroovySSLModel(Binding binding) {
        sensorDataList = new HashMap<>();
        this.binding = binding;
    }


    public void createSource(String name, String address) throws IOException {
        String type = address.split(".")[1];
        sensorDataList.put(name, DataSourceFactory.getDataSource(type).readContent(address));
    }

    // TODO : have to be changed : need to put value type in the dsl ?
    public void putNoise(int boundary, String name) throws Exception {
        if (sensorDataList.get(name).getSensorDataList().isEmpty()){
            throw new Exception("Dataset can't be null");
        }
        sensorDataList.get(name).getSensorDataList()
                .stream().forEach(sensor -> {
            Integer value = ((SensorData<Integer>) sensor).getValue();
            ((SensorData<Integer>) sensor).setValue(value + new Random().nextInt(boundary));
        });
    }

    public void changeOffset(String dateFrom, String name) throws ParseException {
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(dateFrom);
        java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(timeStampDate);
        sensorDataList.get(name).getSensorDataList().forEach(sensor -> {
            c.add(Calendar.MINUTE, 1);
            timeStampDate.setTime(c.getTime().getTime());
            ((SensorData) sensor).setTime(timeStampDate.getTime());
        });
    }


    Object generateCode(String name) {
        System.out.println(sensorDataList);
        return null;
    }
}
