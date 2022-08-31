package bg.adi.travel.util;

import org.springframework.data.domain.Sort;

public class SortingUtil {
	private SortingUtil() {}
	
	public static Sort.Direction replaceOrderStringThroughDirection(String sortDirection) {
        if (sortDirection.equalsIgnoreCase("DESC")) {
            return Sort.Direction.DESC;
        } else {
            return Sort.Direction.ASC;
        }
    }
}
