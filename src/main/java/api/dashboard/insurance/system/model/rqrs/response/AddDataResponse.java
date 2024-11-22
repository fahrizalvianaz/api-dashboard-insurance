package api.dashboard.insurance.system.model.rqrs.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddDataResponse {
    private String description;
}
