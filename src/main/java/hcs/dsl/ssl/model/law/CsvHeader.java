package hcs.dsl.ssl.model.law;

public class CsvHeader extends Header {

    private static final int ERROR_INDEX = -1;

    private Integer f1Index;
    private Integer f2Index;
    private Integer f3Index;

    public Integer getF1Index() {
        return f1Index;
    }

    public void setF1Index(Integer f1Index) {
        this.f1Index = f1Index;
    }

    public Integer getF2Index() {
        return f2Index;
    }

    public void setF2Index(Integer f2Index) {
        this.f2Index = f2Index;
    }

    public Integer getF3Index() {
        return f3Index;
    }

    public void setF3Index(Integer f3Index) {
        this.f3Index = f3Index;
    }


    public int indexOf(HeaderType type) {
        return f1Type == type ? f1Index
                : f2Type == type ? f2Index
                : f3Type == type ? f3Index
                : defaultIndexOf(type);
    }

    private int defaultIndexOf(HeaderType type) {
        return type == HeaderType.TIME ? 0
                : type == HeaderType.NAME ? 1
                : type == HeaderType.VALUE ? 2
                : ERROR_INDEX;
    }

    private void checkAlready(HeaderType type) {
        if (type == f1Type || type == f2Type || type == f3Type) {
            throw new IllegalArgumentException("header '" + type + "' already defined in CSV headers");
        }
    }
}
