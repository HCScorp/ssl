package kernel.formula.law;


import kernel.datasource.SourceReader;

import java.util.Map;

public class LawFactory {

    private static Map<String, LawDescriber> instances;

    static {
        instances.put("random", new RandomLaw());
    }


    public static <T extends LawDescriber> T getDataSource(String source) {
        return (T) instances.get(source);
    }
}
