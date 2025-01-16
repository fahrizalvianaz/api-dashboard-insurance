package api.dashboard.insurance.system.model.rqrs.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SecondaryRow;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class RefreshFromExcelResponse {
    public String message;
}
