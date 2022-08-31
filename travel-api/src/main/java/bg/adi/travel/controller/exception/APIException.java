package bg.adi.travel.controller.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIException extends RuntimeException {
    private String errorName;
    private String errorDescription;
    private Integer statusCode;
}
