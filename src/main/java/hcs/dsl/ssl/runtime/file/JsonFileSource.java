package hcs.dsl.ssl.runtime.file;

import java.io.Serializable;

public class JsonFileSource<T extends Serializable> extends FileSource<T> {
    public JsonFileSource(String uri) {
        super(uri); // TODO



    }

    @Override
    public T produceValue(long timestamp) {
        return null; // TODO
    }
}
