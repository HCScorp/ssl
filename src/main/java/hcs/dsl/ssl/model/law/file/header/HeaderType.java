package hcs.dsl.ssl.model.law.file.header;

public enum HeaderType {
    TIME,
    VALUE,
    NAME;

    public static HeaderType fromString(String str) {
        try {
            return HeaderType.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("header '" + str + "' does not exist (possible values are 'time', 'value' and 'name')", e);
        }
    }
}
