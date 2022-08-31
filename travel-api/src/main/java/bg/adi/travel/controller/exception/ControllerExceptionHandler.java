package bg.adi.travel.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    public static final String UNKNOWN_EXCEPTION_NAME = "An unknown error has occurred!";
    public static final String UNKNOWN_EXCEPTION_DESCRIPTION = "Unknown exception!";
    private static final String stackTraceLog = "Stack trace: ";

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<APIResponse> handleException(Exception ex) {
        LOGGER.error(stackTraceLog, ex);
        return ResponseEntity
                .status(500)
                .body(new APIResponse(UNKNOWN_EXCEPTION_NAME, UNKNOWN_EXCEPTION_DESCRIPTION));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseBody
    public ResponseEntity<APIResponse> handleException(IllegalArgumentException ex) {
        LOGGER.error(stackTraceLog, ex);
        return ResponseEntity
                .status(400)
                .body(new APIResponse("Illegal arguments passed to API",
                        ex.getMessage() != null ? ex.getMessage() : UNKNOWN_EXCEPTION_DESCRIPTION));
    }

    @ExceptionHandler(value = {APIException.class})
    @ResponseBody
    public ResponseEntity<APIResponse> handleException(APIException ex) {
        LOGGER.error(stackTraceLog, ex);
        if (ex != null) {
            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(new APIResponse(ex.getErrorName(), ex.getErrorDescription()));
        } else {
            return ResponseEntity
                    .status(500)
                    .body(new APIResponse(UNKNOWN_EXCEPTION_NAME, "Unknown exception"));
        }
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<APIResponse> handleException(MethodArgumentNotValidException ex) {
        LOGGER.error(stackTraceLog, ex);
        return ResponseEntity.badRequest().body(
               new APIResponse("Not valid entity!", "MethodArgumentNotValidException")
        );
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseBody
    public ResponseEntity<APIResponse> handleException(HttpMessageNotReadableException ex) {
        LOGGER.error(stackTraceLog, ex);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseBody
    public ResponseEntity<APIResponse> handleException(AccessDeniedException ex) {
        LOGGER.error(stackTraceLog, ex);
        return ResponseEntity
                .status(401)
                .body(
                        new APIResponse("AccessDenied!", "User doesn't have permission to call this service!")
                );
    }
    
    @ExceptionHandler({BadCredentialsException.class})
	public ResponseEntity<APIResponse> handleException(BadCredentialsException ex) {
		LOGGER.error(stackTraceLog, ex);
		return ResponseEntity
				.status(401)
				 .body(
	                        new APIResponse("AccessDenied!", "Invalid credentials!")
	                );
	}
	
	@ExceptionHandler({DisabledException.class})
	public ResponseEntity<APIResponse> handleException(DisabledException ex) {
		LOGGER.error(stackTraceLog, ex);
		return ResponseEntity
				.status(401)
				 .body(
	                        new APIResponse("AccessDenied!", "Disabled user!")
	                );
	}

    @ExceptionHandler(value = {NoSuchElementException.class})
    @ResponseBody
    public ResponseEntity<APIResponse> handleException(NoSuchElementException ex) {
        LOGGER.error(stackTraceLog, ex);
        return ResponseEntity
                .status(404)
                .body(
                       new APIResponse("Couldn't find anything about this search!",
                                "Such an item may not exist or you may not have access to it!")
                );
    }
}
