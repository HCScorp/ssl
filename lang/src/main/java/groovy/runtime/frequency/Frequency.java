package runtime.frequency;

import java.time.Duration;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Frequency {

    private final Duration value;
    private Instant lastUpdate;

    public Frequency(String f) {
        Pattern pattern = Pattern.compile("(\\d+)(ns|ms|s|m|h|d)");
        Matcher matcher = pattern.matcher(f);
        if (!matcher.matches() || matcher.groupCount() != 2) {
            throw new IllegalArgumentException("'" + f + "' is not a valid frequency (only units ns, ms, s, h, m, d are supported)");
        }

        String valStr = matcher.group(1);
        String unitStr = matcher.group(2);

        Long val = Long.valueOf(valStr);
        if (!(val > 0)) {
            throw new IllegalArgumentException("frequency '" + f + "' must be greater than 0");
        }

        switch (unitStr) {
            case "ns":
                this.value = Duration.ofNanos(val);
                break;
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

    public void update() {
        lastUpdate = Instant.now();
    }

    public boolean canUpdate() {
        return Duration.between(lastUpdate, Instant.now()).getNano() > value.getNano();
    }

    // TODO maybe a updateIfPossible that returns a boolean to combine these two functions ?
}
