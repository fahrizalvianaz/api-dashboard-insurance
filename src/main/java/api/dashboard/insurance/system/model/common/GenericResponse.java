package api.dashboard.insurance.system.model.common;

import api.dashboard.insurance.system.model.enums.Status;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GenericResponse<T> {
    private Status status;
    private T response;

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
}
