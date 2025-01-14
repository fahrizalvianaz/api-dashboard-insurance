package api.dashboard.insurance.system.model.rqrs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelDataRequest implements Serializable {
    private String id;
    private LocalDate entryDate;
    private Integer estimateLoss;
}
