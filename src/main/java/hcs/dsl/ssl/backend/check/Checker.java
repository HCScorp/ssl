package hcs.dsl.ssl.backend.check;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Checker {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void checkOffset(String offset) {
        try {
            LocalDateTime.parse(offset, dtf);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("invalid offset date: ", e);
        }
    }

}
