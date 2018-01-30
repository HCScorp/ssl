package ssl.dsl;

import groovy.lang.Binding;
import kernel.datasource.JsonSourceReader;
import kernel.entity.SensorDataList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroovySSLModel {

    private Binding binding;

    SensorDataList sensorDataList;

    GroovySSLModel(Binding binding){
        this.binding = binding;
    }


    public void createSource(String adress) throws IOException {
        // simple have to be combined with abstract factory.
        JsonSourceReader jsonSourceReader = new JsonSourceReader();
        sensorDataList = jsonSourceReader.readContent(adress);
    }
    Object generateCode(String name){
        System.out.println("Generate the code ....");
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        return a;
    }
}
