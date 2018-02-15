package hcs.dsl.ssl.model.law;

import java.util.Optional;

public class Header {

    private static final String ERROR_NAME = "-_;_E.R.R.O.R_;_-";

    private String f1Name;
    protected HeaderType f1Type;

    private String f2Name;
    protected HeaderType f2Type;

    private String f3Name;
    protected HeaderType f3Type;

    public String getF1Name() {
        return f1Name;
    }

    public Optional<String> nameOf(HeaderType type) {
        return Optional.ofNullable(
                f1Type == type ? f1Name
                        : f2Type == type ? f2Name
                        : f3Type == type ? f3Name
                        : null);
    }

    public String nameOrDefaultOf(HeaderType type) {
        Optional<String> nameOpt = nameOf(type);
        return nameOpt.orElse(defaultNameOf(type));
    }

    private String defaultNameOf(HeaderType type) {
        return type == HeaderType.TIME ? "time"
                : type == HeaderType.NAME ? "name"
                : type == HeaderType.VALUE ? "value"
                : ERROR_NAME;
    }

    public boolean isSet(HeaderType type) {
        return f1Type == type || f2Type == type || f3Type == type;
    }

    public void setF1Name(String f1Name) {
        this.f1Name = f1Name;
    }

    public HeaderType getF1Type() {
        return f1Type;
    }

    public void setF1Type(String type) {
        HeaderType h = HeaderType.fromString(type);
        checkAlready(h);
        this.f1Type = h;
    }

    public String getF2Name() {
        return f2Name;
    }

    public void setF2Name(String f2Name) {
        this.f2Name = f2Name;
    }

    public HeaderType getF2Type() {
        return f2Type;
    }

    public void setF2Type(String type) {
        HeaderType h = HeaderType.fromString(type);
        checkAlready(h);
        this.f2Type = h;
    }

    public String getF3Name() {
        return f3Name;
    }

    public void setF3Name(String f3Name) {
        this.f3Name = f3Name;
    }

    public HeaderType getF3Type() {
        return f3Type;
    }

    public void setF3Type(String type) {
        HeaderType h = HeaderType.fromString(type);
        checkAlready(h);
        this.f3Type = h;
    }

    private void checkAlready(HeaderType type) {
        if (type == f1Type || type == f2Type || type == f3Type) {
            throw new IllegalArgumentException("header '" + type + "' already defined in headers");
        }
    }
}
