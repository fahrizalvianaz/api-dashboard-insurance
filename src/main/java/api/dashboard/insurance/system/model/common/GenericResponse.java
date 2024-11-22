package api.dashboard.insurance.system.model.common;

import api.dashboard.insurance.system.exception.CommonException;
import api.dashboard.insurance.system.model.enums.ExceptionEnum;
import api.dashboard.insurance.system.model.enums.Status;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;

@Data
@Accessors(chain = true)
public class GenericResponse<T> {
    private Status status;
    private T response;
    private CommonException exception;

    public boolean isOK() {
        return status.equals(Status.ok);
    }

    public boolean isNOK() {
        return !status.equals(Status.ok);
    }

    public boolean isFailed() {
        return status.equals(Status.failed);
    }

    public boolean isError() {
        return status.equals(Status.error);
    }

    public GenericResponse<T> setSuccess() {
        this.status = Status.ok;
        return this;
    }

    public GenericResponse<T> setError() {
        this.status = Status.error;
        return this;
    }

    public GenericResponse<T> setFailed() {
        this.status = Status.failed;
        return this;
    }

    public GenericResponse<T> setException(Exception e) {
        if (e instanceof CommonException) {
            this.exception = (CommonException) e;
        } else if (e  instanceof DataIntegrityViolationException) {
            this.exception = new CommonException(ExceptionEnum.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } else {
            this.exception = new CommonException(e);
        }
        return this;
    }
}
