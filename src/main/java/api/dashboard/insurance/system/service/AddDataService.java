package api.dashboard.insurance.system.service;

import api.dashboard.insurance.system.model.common.GenericResponse;
import api.dashboard.insurance.system.model.entity.Identity;
import api.dashboard.insurance.system.model.enums.Status;
import api.dashboard.insurance.system.model.rqrs.request.AddDataRequest;
import api.dashboard.insurance.system.model.rqrs.response.AddDataResponse;
import api.dashboard.insurance.system.model.rqrs.response.ReadFromExcelResponse;
import api.dashboard.insurance.system.repository.IdentifyRepository;
import api.dashboard.insurance.system.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddDataService {

    @Autowired
    private IdentifyRepository identifyRepository;

    public GenericResponse<AddDataResponse> execute(AddDataRequest request) {

        GenericResponse<AddDataResponse> result = new GenericResponse<>();

        String token = JwtUtil.generateToken(request.getUsername());
        Identity identity = new Identity();
        try {
            identity.setFileLocation(request.getFileLocation());
            identity.setUsername(request.getUsername());
            identity.setSheet(request.getSheet());
            identity.setToken(token);
            identifyRepository.save(identity);
        } catch (Exception e) {
            result.setStatus(Status.error);
        }

        AddDataResponse response = new AddDataResponse()
                .setDescription("Add data to db done");

        result.setStatus(Status.ok);
        result.setResponse(response);
        return result;
    }
}
