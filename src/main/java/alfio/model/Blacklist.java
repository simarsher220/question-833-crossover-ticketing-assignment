package alfio.model;

import alfio.exception.CustomException;
import alfio.model.result.ErrorCode;
import ch.digitalfondue.npjt.ConstructorAnnotationRowMapper.Column;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class Blacklist {

    private String id;
    private String email;

    public Blacklist(@Column("id") String id,
                     @Column("email") String email) {
        this.id = id;
        this.email = email;
    }

    public static void validateForUpsertion(Blacklist request) throws CustomException {
        request.validate();
    }

    private void validate() throws CustomException {
        validateEmail();
    }

    private void validateEmail() throws CustomException {
        EmailValidator validator = EmailValidator.getInstance();
        if (StringUtils.isEmpty(this.email) || !validator.isValid(this.email)) {
            ErrorCode errorCode = ErrorCode.custom("bad_request", "Email is empty or invalid!");
            throw new CustomException(errorCode, HttpStatus.BAD_REQUEST);
        }
    }
}
