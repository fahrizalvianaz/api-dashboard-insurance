package api.dashboard.insurance.system.exception;
/*
 * @author HermanW [hwahyudi@xl.co.id]
 * created at 20/02/2020 10:47
 */

import api.dashboard.insurance.system.model.enums.ExceptionEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class CommonException extends Exception {
    private static final long serialVersionUID = 1L;

    private ExceptionEnum exceptionEnum;
    private String code;
    private String name;
    private String description;
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public CommonException(ExceptionEnum exceptionEnum, String description) {
        super(String.format("%s -> %s | %s", exceptionEnum.code, exceptionEnum.name(), description));
        log.error(super.getMessage(), this);

        this.name = exceptionEnum.name();
        this.code = exceptionEnum.code;
        this.description = description;

        this.exceptionEnum = exceptionEnum;
    }    
    
    public CommonException(ExceptionEnum exceptionEnum) {
        super(String.format("%s -> %s | %s", exceptionEnum.code, exceptionEnum.name(), exceptionEnum.description));
        log.error(super.getMessage(), this);

        this.name = exceptionEnum.name();
        this.code = exceptionEnum.code;
        this.description = exceptionEnum.description;

        this.exceptionEnum = exceptionEnum;
    }
    
    public CommonException(String code, String name, String description) {
        super(String.format("%s -> %s | %s", code, name, description));
        log.error(super.getMessage(), this);

        this.name = name;
        this.code = code;
        this.description = description;
    }

    public CommonException(Exception e) {
        super(String.format("%s -> %s | %s", ExceptionEnum.SER_UNEXPECTED_ERROR.code, ExceptionEnum.SER_UNEXPECTED_ERROR.name(), ExceptionUtils.getRootCauseMessage(e)), e.getCause());
        log.error(super.getMessage(), e);

        this.exceptionEnum = ExceptionEnum.SER_UNEXPECTED_ERROR;
        this.name = exceptionEnum.name();
        this.code = exceptionEnum.code;
        this.description = e.getMessage();

        this.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
