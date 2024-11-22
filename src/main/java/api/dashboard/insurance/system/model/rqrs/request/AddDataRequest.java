package api.dashboard.insurance.system.model.rqrs.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddDataRequest {
    private String username;
    private String fileLocation;
    private Integer sheet;
}
