package api.dashboard.insurance.system.model.rqrs.info;

import api.dashboard.insurance.system.exception.CommonException;
import api.dashboard.insurance.system.model.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ResponseInfo {

    private Status status;
    private int responseTime;
    private HttpStatus httpStatus;
    private Object response;
    private List<CommonException> exceptions = new ArrayList<>();

    public ResponseInfo addException(Exception e) {
        this.exceptions.add(new CommonException(e));
        return this;
    }

    public ResponseInfo addException(CommonException e) {
        this.exceptions.add(e);
        return this;
    }

}
