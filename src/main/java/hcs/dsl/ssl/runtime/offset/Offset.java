package hcs.dsl.ssl.runtime.offset;


public class Offset {

    private final long offset;

    public Offset(String date) {
        // TODO parse date
        this.offset = 0; // TODO
    }

    public Offset(long offset) {
        throw new UnsupportedOperationException();
    }

    public long apply(long timestamp) {
        return timestamp + offset;
    }

//    // Any offset apply on the produced
//    void setTimestampOffset(int offset) {
//        // TODO
//        throw new NotImplementedException();
//    }

    // set start date at "date" ("date" can be a date or TODAY)
//
//    void setStartDate(String date) { // OR LocalDate ?
//        // Here date can be a timestamp or a formated date ???
//
//        // TODO compute seconds between execution time and minimum timestamp of data source, then apply this offset
//    }
//
//    void setStartDate(int timestamp) { // OR Instant ?
//        // TODO compute seconds between execution time and minimum timestamp of data source, then apply this offset
//    }
}