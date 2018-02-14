package hcs.dsl.ssl.model.law;

public class JsonHeader {
    private String f1Name;
    private Header f1Type;

    private String f2Name;
    private Header f2Type;

    private String f3Name;
    private Header f3Type;

    public String getF1Name() {
        return f1Name;
    }

    public void setF1Name(String f1Name) {
        this.f1Name = f1Name;
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

    public Header getF3Type() {
        return f3Type;
    }

    public void setF3Type(String type) {
        Header h = Header.fromString(type);
        checkAlready(h);
        this.f3Type = h;
    }

    private void checkAlready(Header type) {
        if (type == f1Type || type == f2Type || type == f3Type) {
            throw new IllegalArgumentException("header '" + type + "' already defined in CSV headers");
        }
    }
}
