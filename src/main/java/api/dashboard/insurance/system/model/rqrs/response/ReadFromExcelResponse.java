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

    private int totalOverThan100M = 0;
    private int totalOverThan50M = 0;
    private int totalOverThan25M = 0;
    private int totalLessThan25M = 0;
    private int totalUnknown = 0;

}
