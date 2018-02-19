package hcs.dsl.ssl.model.law.file;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import hcs.dsl.ssl.model.law.file.header.CsvHeader;
import hcs.dsl.ssl.model.law.file.header.HeaderType;
import hcs.dsl.ssl.model.misc.VarType;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hcs.dsl.ssl.model.misc.CheckHelper.PATTERN_TIMESTAMP;

public class FileLawCsv extends FileLaw {

    private CsvHeader csvHeader;
    private CsvMapper mapper = new CsvMapper();

    public FileLawCsv(String lawName, String fileUri, String sensorName) {
        super(lawName, fileUri, sensorName, FileType.CSV);
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
    }

    public CsvHeader getCsvHeader() {
        return csvHeader;
    }

    public void setCsvHeader(CsvHeader csvHeader) {
        this.csvHeader = csvHeader;
    }

    @Override
    protected void fillData() throws IOException {
        MappingIterator<String[]> it = mapper.readerFor(String[].class).readValues(file);
        List<String[]> entries = it.readAll();

        if (entries.isEmpty()) {
            return;
        }

        String[] firstEntry = entries.get(0);
        boolean headerPresent = headerPresent(firstEntry);

        if (headerNameSet() && !headerPresent) {
            throw new IllegalArgumentException("the script set specific headers for file '" + getFileUri() +
                    "' but the CSV header appears to be missing");
        }

        if (headerPresent) {
            entries.remove(0);
        }

        // Resolve indexes for each sensor data to be gathered
        int indexTime = fetchIndexOf(HeaderType.TIME, headerPresent, firstEntry);
        int indexName = fetchIndexOf(HeaderType.NAME, headerPresent, firstEntry);
        int indexValue = fetchIndexOf(HeaderType.VALUE, headerPresent, firstEntry);

        // Resolve value type
        String firstValue = firstEntry[indexValue];
        VarType typeData = resolveValueType(firstValue);
        setValType(typeData);

        // Parse CSV
        data = entries.stream()
                .filter(raw -> sensorName.equals(raw[indexName]))
                .map(raw -> new SensorData(parseTimestamp(raw[indexTime]), sensorName, typeData, raw[indexValue]))
                .collect(Collectors.toList());
    }

    private int fetchIndexOf(HeaderType type, boolean headerPresent, String[] firstEntry) {
        int index = fetchIndexOfInsecure(type, headerPresent, firstEntry);
        if (index < 0 || index > firstEntry.length) {
            throw new IllegalArgumentException("column of index " + index + " can't be found in th CSV file '" + getFileUri() + "'");
        }

        return index;
    }

    private int fetchIndexOfInsecure(HeaderType type, boolean headerPresent, String[] firstEntry) {
        if (headerPresent) {
            Optional<String> hNameOpt = csvHeader.nameOf(type);
            if (hNameOpt.isPresent()) {
                String hName = hNameOpt.get();
                for (int i = 0; i < firstEntry.length; i++) {
                    if (hName.equals(firstEntry[i])) {
                        return i;
                    }
                }
                throw new IllegalArgumentException("column '" + hName + "' can't be found in th CSV file '" + getFileUri() + "'");
            }
        }

        return csvHeader.indexOf(type);
    }

    /**
     * We consider that a header is present if the first line does not contains any timestamp
     *
     * @param firstEntry
     * @return
     */
    private boolean headerPresent(String[] firstEntry) {
        for (String s : firstEntry) {
            if (PATTERN_TIMESTAMP.matcher(s).matches()) {
                return false;
            }
        }
        return true;
    }

    private boolean headerNameSet() {
        return csvHeader.getF1Name() != null
                || csvHeader.getF2Name() != null
                || csvHeader.getF3Name() != null;
    }
}
