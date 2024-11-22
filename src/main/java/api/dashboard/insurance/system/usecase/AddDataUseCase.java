package api.dashboard.insurance.system.usecase;

import api.dashboard.insurance.system.model.common.AdapterResponse;
import api.dashboard.insurance.system.model.common.GenericResponse;
import api.dashboard.insurance.system.model.rqrs.request.AddDataRequest;
import api.dashboard.insurance.system.model.rqrs.response.AddDataResponse;
import api.dashboard.insurance.system.model.rqrs.response.ReadFromExcelResponse;
import api.dashboard.insurance.system.service.AddDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddDataUseCase {

    @Autowired
    private AddDataService addDataService;

    public AdapterResponse<AddDataResponse> execute(AddDataRequest request) {
        AdapterResponse<AddDataResponse> response = new AdapterResponse<>();
        GenericResponse<AddDataResponse> result = addDataService.execute(request);
        if(result.isOK()) {
            response.setData(result.getResponse());
        } else if (result.isError()) {
            response.setCode("99");
        }
        return response;
    }
}
