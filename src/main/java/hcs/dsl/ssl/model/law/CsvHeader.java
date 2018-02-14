package hcs.dsl.ssl.model.law;

import hcs.dsl.ssl.model.misc.Var;

public class CsvHeader {

    private String f1Name;
    private Integer f1Index;
    private Header f1Type;

    private String f2Name;
    private Integer f2Index;
    private Header f2Type;

    private String f3Name;
    private Integer f3Index;
    private Header f3Type;

    public String getF1Name() {
        return f1Name;
    }

    public void setF1Name(String f1Name) {
        this.f1Name = f1Name;
    }

    public Integer getF1Index() {
        return f1Index;
    }

    public void setF1Index(Integer f1Index) {
        this.f1Index = f1Index;
    }

    public Header getF1Type() {
        return f1Type;
    }

    public void setF1Type(String type) {
        Header h = Header.fromString(type);
        checkAlready(h);
        this.f1Type = h;
    }

    public String getF2Name() {
        return f2Name;
    }

    public void setF2Name(String f2Name) {
        this.f2Name = f2Name;
    }

    public Integer getF2Index() {
        return f2Index;
    }

    public void setF2Index(Integer f2Index) {
        this.f2Index = f2Index;
    }

    public Header getF2Type() {
        return f2Type;
    }

    public void setF2Type(String type) {
        Header h = Header.fromString(type);
        checkAlready(h);
        this.f2Type = h;
    }

    public String getF3Name() {
        return f3Name;
    }

    public void setF3Name(String f3Name) {
        this.f3Name = f3Name;
    }

    public Integer getF3Index() {
        return f3Index;
    }

    public void setF3Index(Integer f3Index) {
        this.f3Index = f3Index;
    }

    public Header getF3Type() {
        return f3Type;
    }

    public void setF3Type(String type) {
        Header h = Header.fromString(type);
        checkAlready(h);
        this.f3Type = h;
    }

    public int indexValue(Header type){
        return (f1Type == type)?f1Index:
                (f2Type == type)?f2Index:
                        f3Index;
    }


    private void checkAlready(Header type) {
        if (type == f1Type || type == f2Type || type == f3Type) {
            throw new IllegalArgumentException("header '" + type + "' already defined in CSV headers");
        }
    }
}
