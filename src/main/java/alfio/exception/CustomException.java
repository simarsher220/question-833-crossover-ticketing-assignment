package alfio.exception;

import alfio.model.result.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CustomException extends Exception {

    private final ErrorCode code;
    private final HttpStatus status;
}

