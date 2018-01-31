package kernel.datasource;

import java.util.HashMap;
import java.util.Map;

public class DataSourceFactory {
    private static Map<String, SourceReader> instances;

    static {
        instances = new HashMap<>();
        instances.put("json", new JsonSourceReader());
    }

    public static <T extends SourceReader> T getDataSource(String source){
        return (T) instances.get(source);
    }

}
