package hcs.dsl.ssl.model.misc;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class CheckHelper {

    public static final Pattern PATTERN_INTEGER = Pattern.compile("[-+]?\\d+");
    public static final Pattern PATTERN_DOUBLE = Pattern.compile("\\d+\\.\\d*");
    public static final Pattern PATTERN_BOOLEAN = Pattern.compile("(?:true|TRUE|false|FALSE)");
    public static final Pattern PATTERN_TIMESTAMP = Pattern.compile("\\d{10}");

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static LocalDateTime checkDate(String offset) {
        try {
            return LocalDateTime.parse(offset, dtf);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("invalid date: ", e);
        }
    }
}
