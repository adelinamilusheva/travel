package bg.adi.travel.util;

import java.util.NoSuchElementException;
import java.util.Optional;

public class ValidationUtil {
    private ValidationUtil() {}

    public static void validate(Optional<? extends Object> obj) {
        if (obj.isEmpty()) {
            throw new NoSuchElementException();
        }
    }
}
