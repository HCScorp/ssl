package runtime.offset;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Offset {
    // Any offset apply on the produced
    void setTimestampOffset(int offset) {
        // TODO
        throw new NotImplementedException();
    }

    // set start date at "date" ("date" can be a date or TODAY)

    void setStartDate(String date) { // OR LocalDate ?
        // Here date can be a timestamp or a formated date ???

        // TODO compute seconds between execution time and minimum timestamp of data source, then apply this offset
    }

    void setStartDate(int timestamp) { // OR Instant ?
        // TODO compute seconds between execution time and minimum timestamp of data source, then apply this offset
    }
}
