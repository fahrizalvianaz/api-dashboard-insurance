package api.dashboard.insurance.system.model.common;

import api.dashboard.insurance.system.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdapterResponse<T> {
    private Status status;
    private String code;
    private String message;
    private T data;


    public AdapterResponse<T> setSuccess() {
        this.status = Status.ok;
        this.code = "00";
        this.message = "success";

        return this;
    }

    @JsonIgnore
    public AdapterResponse<T> setError(String err) {
        this.status = Status.error;
        this.code = "99";
        this.message = err;

        return this;
    }

    @JsonIgnore
    public AdapterResponse<T> setFailed(String failed) {
        this.status = Status.failed;
        this.code = "01";
        this.message = failed;

        return this;
    }

}

