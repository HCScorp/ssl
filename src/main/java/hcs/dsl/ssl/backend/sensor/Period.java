package hcs.dsl.ssl.backend.sensor;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Period {

    private final Duration value;

    public Period(String f) {
            Pattern pattern = Pattern.compile("(\\d+)(ms|s|m|h|d)");
        Matcher matcher = pattern.matcher(f);
        if (!matcher.matches() || matcher.groupCount() != 2) {
            throw new IllegalArgumentException("'" + f + "' is not a valid frequency (only units ms, s, h, m, d are supported)");
        }


        String valStr = matcher.group(1);
        String unitStr = matcher.group(2);

        Long val = Long.valueOf(valStr);
        if (!(val > 0)) {
            throw new IllegalArgumentException("frequency '" + f + "' must be greater than 0");
        }

        switch (unitStr) {
            case "ms":
                this.value = Duration.ofMillis(val);
                break;
            case "s":
                this.value = Duration.ofSeconds(val);
                break;
            case "m":
                this.value = Duration.ofMinutes(val);
                break;
            case "h":
                this.value = Duration.ofHours(val);
                break;
            case "d":
                this.value = Duration.ofDays(val);
                break;
            default:
                throw new IllegalArgumentException("'" + unitStr + "' is not a valid frequency unit"); // should never happen
        }
    }

    public long getPeriod() {
        return (value.getSeconds() * 1000) + (value.getNano() / 1000000);
    }
}
