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

    private long totalOverThan100M = 0;
    private long totalOverThan50M = 0;
    private long totalOverThan25M = 0;
    private long totalLessThan25M = 0;
    private long totalUnknown = 0;

}
