package api.dashboard.insurance.system.usecase;

import api.dashboard.insurance.system.model.common.AdapterResponse;
import api.dashboard.insurance.system.model.common.GenericResponse;
import api.dashboard.insurance.system.model.rqrs.info.ResponseInfo;
import api.dashboard.insurance.system.model.rqrs.request.AddDataRequest;
import api.dashboard.insurance.system.model.rqrs.response.AddDataResponse;
import api.dashboard.insurance.system.model.rqrs.response.ReadFromExcelResponse;
import api.dashboard.insurance.system.service.AddDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.w3c.dom.html.HTMLTableCaptionElement;

@Service
public class AddDataUseCase {

    @Autowired
    private AddDataService addDataService;

    public ResponseInfo execute(AddDataRequest request) {
        ResponseInfo responseInfo = new ResponseInfo();
        HttpStatus httpStatus = HttpStatus.OK;
        AdapterResponse<AddDataResponse> adapterResponse = new AdapterResponse<>();
        GenericResponse<AddDataResponse> genericResponse = addDataService.execute(request);
        if(genericResponse.isOK()) {
            adapterResponse.setData(genericResponse.getResponse());
        } else if (genericResponse.isError()) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            adapterResponse.setCode("99").setMessage(genericResponse.getException().getDescription());
        }
        responseInfo
                .setHttpStatus(httpStatus)
                .setStatus(genericResponse.getStatus())
                .setResponse(adapterResponse.setStatus(genericResponse.getStatus()))
                .addException(genericResponse.getException());
        return responseInfo;
    }
}
