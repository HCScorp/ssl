package hcs.dsl.ssl.runtime.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.UrlValidator;
import hcs.dsl.ssl.runtime.source.Source;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

public abstract class FileSource<T extends Serializable> extends Source<T> {

    protected final File file;

    public FileSource(String uri) {
        try {
            UrlValidator urlValidator = new UrlValidator();
            if (urlValidator.isValid(uri)) {
                this.file = File.createTempFile("datasource", ".tmp");
                FileUtils.copyURLToFile(new URL(uri), file);
            } else {
                this.file = new File(uri);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // TODO fetch
}


// TODO take a path to file or an url
// TODO when the file is fetch (csv or json) use a parser to build the list of tuple
// TODO save the minimum timestamp
// TODO compute the polynomial function or save the value is no interpolation

// TODO linear interpolation only for integer and double

// TODO method to call to parse the file
// TODO method to call to interpolate if needed

