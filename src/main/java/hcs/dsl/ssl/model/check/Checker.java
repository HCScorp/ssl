package hcs.dsl.ssl.model.check;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Checker {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static LocalDateTime checkDate(String offset) {
        try {
            return LocalDateTime.parse(offset, dtf);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("invalid date: ", e);
        }
    }

}
