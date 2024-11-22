package api.dashboard.insurance.system.model.rqrs.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ReadFromExcelResponse {

    private Integer total;

}
