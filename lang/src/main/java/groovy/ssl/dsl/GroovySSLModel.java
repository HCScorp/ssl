package ssl.dsl;

import groovy.lang.Binding;
import kernel.datasource.DataSourceFactory;
import kernel.datasource.JsonSourceReader;
import kernel.entity.SensorDataList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroovySSLModel {

    private Binding binding;

    private SensorDataList sensorDataList;
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();


    GroovySSLModel(Binding binding) {
        this.binding = binding;
    }


    public void createSource(String type, String address) throws IOException {
        System.out.println("Create source");
        sensorDataList = DataSourceFactory.getDataSource(type).readContent(address);
    }

    Object generateCode(String name) {
        System.out.println("Generate the code ....");
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        return a;
    }
}
