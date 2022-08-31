package bg.adi.travel.util;

import java.time.format.DateTimeFormatter;

public class DateUtil {
    private DateUtil() {}

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
}
