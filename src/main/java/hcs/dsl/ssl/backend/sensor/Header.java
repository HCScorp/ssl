package hcs.dsl.ssl.backend.sensor;

public enum Header {
    TIME,
    VALUE,
    NAME;

    public static Header fromString(String str) {
        try {
            return Header.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("header '" + str + "' does not exist (possible values are 'time', 'value' and 'name')", e);
        }
    }
}
